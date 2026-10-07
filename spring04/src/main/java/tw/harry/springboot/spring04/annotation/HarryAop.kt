package tw.harry.springboot.spring04.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class HarryAop

// @Retention(AnnotationRetention.RUNTIME)
// @Target(ElementType.METHOD)
// public @interface HarryApp {
// }