package com.ead.lib.monoschinos.models.pagination

data class Pagination<T>(
    val totalItems: Int,
    val itemsPerPage: Int,
    val currentPage: Int,
    val totalPages: Int,
    val items: List<T>
)