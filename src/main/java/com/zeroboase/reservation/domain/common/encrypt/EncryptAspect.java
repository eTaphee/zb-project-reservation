package com.zeroboase.reservation.domain.common.encrypt;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
public class EncryptAspect {

    private final EncryptComponent encryptComponent;

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository.save(..)) && args(entity)")
    public void savePointcut(Object entity) {
    }

    @Before(value = "savePointcut(entity)", argNames = "joinPoint,entity")
    public void beforeSave(JoinPoint joinPoint, Object entity)
        throws Exception {
        encryptFields(entity, getEncryptFields(entity.getClass()));
    }

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository+.find*(..))")
    public void findPointcut() {
    }

    @AfterReturning(pointcut = "findPointcut()", returning = "result")
    public void afterFind(Object result) throws Exception {
        if (result instanceof Iterable<?> iterableResult) {
            for (Object entity : iterableResult) {
                decryptFields(entity, getEncryptFields(entity.getClass()));
            }
        } else {
            if (result instanceof Optional<?> optionalResult) {
                if (optionalResult.isPresent()) {
                    decryptFields(optionalResult.get(),
                        getEncryptFields(optionalResult.get().getClass()));
                }
            } else {
                decryptFields(result, getEncryptFields(result.getClass()));
            }
        }
    }

    private Field[] getEncryptFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Encrypt.class))
            .toArray(Field[]::new);
    }

    private void encryptFields(Object entity, Field[] encryptFields)
        throws Exception {
        for (Field field : encryptFields) {
            field.setAccessible(true);
            String plainText = field.get(entity).toString();
            field.set(entity, encryptComponent.encryptString(plainText));
        }
    }

    private void decryptFields(Object entity, Field[] encryptFields)
        throws Exception {
        for (Field field : encryptFields) {
            field.setAccessible(true);
            String cipherText = field.get(entity).toString();
            field.set(entity, encryptComponent.decryptString(cipherText));
        }
    }
}
