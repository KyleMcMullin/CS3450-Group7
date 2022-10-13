package com.cs3450.dansfrappesraps.util

import com.cs3450.dansfrappesraps.ui.viewmodels.SortState

fun userFilterText(filterType: SortState) = when (filterType) {
    SortState.ALL -> "All"
    SortState.CUSTOMER -> "Customer"
    SortState.EMPLOYEE -> "Employee"
    SortState.MANAGER -> "Manager"
    else -> "All"
}