package com.ciabatta.core.domain.exception

open class CafeCartException(message: String) : RuntimeException(message)

class CafeCartNotFoundException : CafeCartException("Cafe cart not found.")
class CafeCartItemNotFoundException : CafeCartException("Cafe cart item not found.")