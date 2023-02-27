package com.gabrieal.carparkavailabilityapplication.models

enum class CategorySizeState(val minLimit: Int, val maxLimit: Int) {
    SMALL(0, 100),
    MEDIUM(SMALL.maxLimit, 300),
    BIG(MEDIUM.maxLimit, 400),
    LARGE(BIG.maxLimit, Int.MAX_VALUE);

    companion object {
        fun getMinMaxLimit(state: String): Pair<Int, Int> {
            return Pair(valueOf(state).minLimit, valueOf(state).maxLimit)
        }
    }
}