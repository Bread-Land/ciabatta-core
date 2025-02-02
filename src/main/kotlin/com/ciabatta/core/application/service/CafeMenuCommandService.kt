package com.ciabatta.core.application.service

import com.ciabatta.core.application.dto.CafeMenuCreateDTO
import com.ciabatta.core.application.dto.CafeMenuUpdateDTO
import com.ciabatta.core.application.port.input.CafeMenuCommandUseCase
import com.ciabatta.core.application.port.input.CafeMenuQueryUseCase
import com.ciabatta.core.application.port.output.CafeMenuCommandPort
import com.ciabatta.core.domain.model.CafeMenu
import org.springframework.stereotype.Service

@Service
class CafeMenuCommandService(
    private val cafeMenuCommandPort: CafeMenuCommandPort,
    private val cafeMenuQueryUseCase: CafeMenuQueryUseCase
) : CafeMenuCommandUseCase {
    override suspend fun createCafeMenu(
        userID: String,
        dto: CafeMenuCreateDTO
    ): CafeMenu {
        return cafeMenuCommandPort.save(CafeMenu.fromCreateDTO(userID, dto).toEntity())
            .let {
                CafeMenu.fromEntity(it)
            }
    }

    override suspend fun updateCafeMenu(
        cafeMenuId: Long,
        userID: String,
        dto: CafeMenuUpdateDTO
    ): CafeMenu? {
        return cafeMenuQueryUseCase.findCafeMenuById(cafeMenuId)
            ?.let { existingMenu ->
                cafeMenuCommandPort.save(CafeMenu.fromUpdateDTO(cafeMenuId, userID, existingMenu, dto).toEntity())
            }
            ?.let { updatedMenu ->
                CafeMenu.fromEntity(updatedMenu)
            }
    }

    override suspend fun deleteCafeMenuById(
        cafeMenuId: Long
    ): Long? {
        return cafeMenuQueryUseCase.findCafeMenuById(cafeMenuId)
            ?.let {
                cafeMenuCommandPort.deleteById(cafeMenuId)
            }
    }
}