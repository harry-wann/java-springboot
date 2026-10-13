package tw.harry.springboot.spring09.exception

class ModelCallException(cause: Throwable)
    : RuntimeException("Model call failed", cause)