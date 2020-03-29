package site.gladmin.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.gladmin.aop.annotation.ExportExl;
import site.gladmin.aop.annotation.ExportParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Author: gladmin
 * @Date: 2020/3/29 2:58 下午
 */

@Aspect
public class ExportInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportInterceptor.class);



    @Pointcut("@annotation(site.gladmin.aop.annotation.ExportExl)")
    public  void pointCut(){}


    @Around("pointCut()")
    public Object intercptor(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();

        Class<?> resultType = method.getReturnType();

        Object[] args = joinPoint.getArgs();

        if (!this.preCheckArgsAndReturnTypes(method,resultType,args)){
            return joinPoint.proceed();
        }

        if (args.length==1){
            return this.handleTheOnlyArg(joinPoint,method,args[0]);
        }

        return this.handleMultiArgs(joinPoint, method, args);
    }
    private boolean preCheckArgsAndReturnTypes(Method method, Class<?> resultType, Object[] args) throws Throwable {
        //出参必须为int或者对应的包装类型Integer
        if (!(Integer.class.getName().equals(resultType.getName()) || "int".equalsIgnoreCase(resultType.getName()))) {
            LOGGER.error("出参不是int!调用原生的方法{}", this.getFullMethodName(method));
            return false;
        }

        //入参列表长度必须大于0(必须要有参数)
        if (args == null || args.length == 0) {
            LOGGER.error("入参长度为0或没有入参!调用原生的方法{}", this.getFullMethodName(method));
            return false;
        }

        return true;
    }

    private Object handleTheOnlyArg(ProceedingJoinPoint joinPoint, Method method, Object arg) throws Throwable {
        if (!preCheckTheOnlyArg(method, arg)) {
            return joinPoint.proceed();
        }

        return this.handleTheOnlyList(joinPoint, method, arg);

    }
    private Object handleTheOnlyList(ProceedingJoinPoint joinPoint, Method method, Object theOnlyList) throws Throwable {
        //单List类型入参 为 多入参且包含唯一有效List类型入参的一种特殊情形
        return this.handleTheMultiArgsForTheOnlyValidList(joinPoint, method, new Object[]{theOnlyList}, 0);
    }

    private boolean preCheckTheOnlyArg(Method method, Object theOnlyArg) {
        //唯一参数不能为空
        if (theOnlyArg == null) {
            LOGGER.error("唯一的参数为null!调用原生的方法{}", this.getFullMethodName(method));
            return false;
        }
        //唯一参数类型必须为List或者Map
        if (!(theOnlyArg instanceof List || theOnlyArg instanceof Map)) {
            LOGGER.error("唯一的参数不为List或Map!调用原生的方法{}", this.getFullMethodName(method));
            return false;
        }

        return true;
    }
    private Object handleMultiArgs(ProceedingJoinPoint pjp, Method method, Object[] args) throws Throwable {
        int listCount = 0;
        int index = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof List) {
                listCount++;
                index = i;
            }
        }

        if (listCount == 0) {
            LOGGER.error("多个参数中没有找到List存在!调用原生的方法{}", this.getFullMethodName(method));
            return pjp.proceed();
        }

        if (listCount == 1) {
            //多入参中仅有一个List类型参数
            return this.handleTheMultiArgsForTheOnlyValidList(pjp, method, args, index);
        }

        return this.handleMultiListArgs(pjp, method, args);
    }
    private Object handleMultiListArgs(ProceedingJoinPoint pjp, Method method, Object[] args) throws Throwable {

        int index = this.getIndexOfTheAnnoListFromMultiListArgs(pjp, method, args);
        if (index < 0) {
            return pjp.proceed();
        }
        //含有多个List类型入参但是其中仅有一个标记了@BatchHacker注解
        return this.handleTheMultiArgsForTheOnlyValidList(pjp, method, args, index);
    }

    private int getIndexOfTheAnnoListFromMultiListArgs(ProceedingJoinPoint pjp, Method method, Object[] args) throws Throwable {
        int annCount = 0;
        int index = 0;

        Annotation[][] paramsAnoAry = method.getParameterAnnotations();

        if (paramsAnoAry != null && paramsAnoAry.length > 0) {
            for (int i = 0; i < paramsAnoAry.length; i++) {
                Annotation[] annotationAry = paramsAnoAry[i];
                if (annotationAry != null && annotationAry.length > 0) {
                    for (Annotation annotation : annotationAry) {
                        if (annotation instanceof ExportParam && args[i] instanceof List) {
                            annCount++;
                            index = i;
                        }
                    }
                }
            }
        }

        if (annCount == 0) {
            LOGGER.error("多个List参数中没有找到BatchHackerParam注解!调用原生的方法{}", this.getFullMethodName(method));
            return -1;
        }

        if (annCount > 1) {
            LOGGER.error("多个List参数中存在大于1个BatchHackerParam注解!调用原生的方法{}", this.getFullMethodName(method));
            return -1;
        }

        return index;
    }
    private Object handleTheMultiArgsForTheOnlyValidList(ProceedingJoinPoint joinPoint, Method method, Object[] args, int index) throws Throwable {

        List data = (List) args[index];

        ExportExl exportExl = method.getAnnotation(ExportExl.class);
        if (!checkExportExlAnnFields(method, data, exportExl)) {
            return joinPoint.proceed();
        }

        return foreachProceedOfMultiArgs(joinPoint, args, index, data, exportExl.aopSize());
    }
    private boolean checkExportExlAnnFields(Method method, List data, ExportExl exportExl) throws Throwable {

        int aopSize = exportExl.aopSize();
        int aopLimit = exportExl.aopLimit();
        if (aopSize > aopLimit) {
            aopSize = aopLimit;
        }

        if (aopLimit <= 0) {
            LOGGER.error("ExportExl注解的aopLimit值{}不在有效范围,应该为大于0的整数!调用原生的方法{}", aopLimit, this.getFullMethodName(method));
            return false;
        }

        if (data == null || data.size() == 0 || data.size() < aopLimit) {
            LOGGER.error("批处理数据List长度不符:{}!调用原生的方法{}", data == null ? 0 : data.size(), this.getFullMethodName(method));
            return false;
        }

        if (aopSize <= 0) {
            LOGGER.error("ExportExl注解的aopSize值{}不在有效范围,应该为({},{}]的整数!调用原生的方法{}", aopSize, 0, aopLimit, this.getFullMethodName(method));
            return false;
        }

        return true;
    }
    private Object foreachProceedOfMultiArgs(ProceedingJoinPoint pjp, Object[] args, int index, List data, int aopSize) throws Throwable {
        int i = 0;
        int count = 0;

        do {
            int start = i;
            int end = i + aopSize < data.size() ? i + aopSize : data.size();
            List subColl = data.subList(start, end);

            args[index] = subColl;

            count += (Integer) pjp.proceed(args);
            i = i + aopSize;
        } while (i < data.size());

        return count;
    }

    private String getFullMethodName(Method method) {
        if (method == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Class cls = method.getDeclaringClass();
        if (cls != null) {
            sb.append(cls.getName());
            sb.append(":");
        }
        sb.append(method.getName());
        return sb.toString();
    }
}
