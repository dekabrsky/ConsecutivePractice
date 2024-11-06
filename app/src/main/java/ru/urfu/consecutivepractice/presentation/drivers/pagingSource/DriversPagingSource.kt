package ru.urfu.consecutivepractice.presentation.drivers.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.urfu.consecutivepractice.domain.repository.IFormulaOneRepository
import ru.urfu.consecutivepractice.presentation.drivers.cache.DriversCache
import ru.urfu.consecutivepractice.presentation.drivers.model.DriverUiModel
import ru.urfu.consecutivepractice.presentation.mapper.FormulaOneUiMapper

class DriversPagingSource(
    private val repository: IFormulaOneRepository,
    private val uiMapper: FormulaOneUiMapper,
    private val cache: DriversCache
): PagingSource<String, DriverUiModel>() {

    override fun getRefreshKey(state: PagingState<String, DriverUiModel>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, DriverUiModel> {
        return runCatching {
            val drivers = repository.getDrivers(
                if (cache.drivers.size > 0) {
                    cache.drivers.size.toString()
                } else null
            )
            cache.drivers.addAll(drivers)
            drivers
        }.mapCatching { drivers ->
            LoadResult.Page(
                data = drivers.map { uiMapper.mapDriver(it) },
                nextKey = if (cache.drivers.size > 0) {
                    cache.drivers.size.toString()
                } else null,
                prevKey = null
            )
        }.getOrElse { return LoadResult.Error(it) }
    }
}