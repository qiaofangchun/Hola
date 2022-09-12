package com.hola.common.ext

import kotlinx.coroutines.flow.flow

fun <P, R> (suspend (P) -> R).asFlow(p: P) = flow<R> {
    emit(this@asFlow.invoke(p))
}

fun <P1, P2, R> (suspend (P1, P2) -> R).asFlow(p1: P1, p2: P2) = flow<R> {
    emit(this@asFlow.invoke(p1, p2))
}

fun <P1, P2, P3, R> (suspend (P1, P2, P3) -> R).asFlow(p1: P1, p2: P2, p3: P3) = flow<R> {
    emit(this@asFlow.invoke(p1, p2, p3))
}

fun <P1, P2, P3, P4, R> (suspend (P1, P2, P3, P4) -> R).asFlow(
    p1: P1,
    p2: P2,
    p3: P3,
    p4: P4
) = flow<R> {
    emit(this@asFlow.invoke(p1, p2, p3, p4))
}

fun <P1, P2, P3, P4, P5, R> (suspend (P1, P2, P3, P4, P5) -> R).asFlow(
    p1: P1,
    p2: P2,
    p3: P3,
    p4: P4,
    p5: P5
) = flow<R> {
    emit(this@asFlow.invoke(p1, p2, p3, p4, p5))
}

fun <P1, P2, P3, P4, P5, P6, R> (suspend (P1, P2, P3, P4, P5, P6) -> R).asFlow(
    p1: P1,
    p2: P2,
    p3: P3,
    p4: P4,
    p5: P5,
    p6: P6
) = flow<R> {
    emit(this@asFlow.invoke(p1, p2, p3, p4, p5, p6))
}

fun <P1, P2, P3, P4, P5, P6, P7, R> (suspend (P1, P2, P3, P4, P5, P6, P7) -> R).asFlow(
    p1: P1,
    p2: P2,
    p3: P3,
    p4: P4,
    p5: P5,
    p6: P6,
    p7: P7
) = flow<R> {
    emit(this@asFlow.invoke(p1, p2, p3, p4, p5, p6, p7))
}

fun <P1, P2, P3, P4, P5, P6, P7, P8, R> (suspend (P1, P2, P3, P4, P5, P6, P7, P8) -> R).asFlow(
    p1: P1,
    p2: P2,
    p3: P3,
    p4: P4,
    p5: P5,
    p6: P6,
    p7: P7,
    p8: P8
) = flow<R> {
    emit(this@asFlow.invoke(p1, p2, p3, p4, p5, p6, p7, p8))
}

fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> (suspend (P1, P2, P3, P4, P5, P6, P7, P8, P9) -> R).asFlow(
    p1: P1,
    p2: P2,
    p3: P3,
    p4: P4,
    p5: P5,
    p6: P6,
    p7: P7,
    p8: P8,
    p9: P9
) = flow<R> {
    emit(this@asFlow.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9))
}