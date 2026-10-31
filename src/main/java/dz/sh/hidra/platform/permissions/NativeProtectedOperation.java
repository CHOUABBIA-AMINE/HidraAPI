package dz.sh.hidra.platform.permissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Intent marker only. Platform's independent, startup-verified route registry
 * must separately approve the compiled controller, signature, verb and pattern.
 * This annotation never grants any native Identity permission.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NativeProtectedOperation {
}
