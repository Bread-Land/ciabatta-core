package com.breadkun.backend.domain.cafe.service

import com.breadkun.backend.domain.cafe.dto.request.CafeMenuCreateDTO
import com.breadkun.backend.domain.cafe.dto.request.CafeMenuUpdateDTO
import com.breadkun.backend.domain.cafe.dto.response.CafeMenuDTO
import com.breadkun.backend.domain.cafe.repository.CafeMenuCommandRepository
import org.springframework.stereotype.Service

interface CafeMenuCommandService {
    suspend fun createCafeMenu(dto: CafeMenuCreateDTO): CafeMenuDTO
    suspend fun updateCafeMenu(id: String, dto: CafeMenuUpdateDTO): CafeMenuDTO?
    suspend fun deleteCafeMenuById(id: String): String?
}

@Service
class CafeMenuCommandServiceImpl(
    private val cafeMenuCommandRepository: CafeMenuCommandRepository,
    private val cafeMenuQueryService: CafeMenuQueryService
) : CafeMenuCommandService {
    override suspend fun createCafeMenu(dto: CafeMenuCreateDTO): CafeMenuDTO {
        return cafeMenuCommandRepository.save(dto.toModel())
            .let {
                CafeMenuDTO.fromModel(it)
            }
    }

    override suspend fun updateCafeMenu(id: String, dto: CafeMenuUpdateDTO): CafeMenuDTO? {
        return cafeMenuQueryService.findCafeMenuById(id)
            ?.let { existingMenu ->
                cafeMenuCommandRepository.update(dto.toModel(id, existingMenu))
            }
            ?.let { updatedMenu ->
                CafeMenuDTO.fromModel(updatedMenu)
            }
    }

    override suspend fun deleteCafeMenuById(id: String): String? {
        return cafeMenuQueryService.findCafeMenuById(id)
            ?.let {
                cafeMenuCommandRepository.deleteById(id)
            }
    }
}