package com.panel.service

import com.loging.logService.models.Content
import org.springframework.data.domain.Page

interface ContentService<E : Content<ID>, ID> {

    fun getContentPage(creatorId: ID, page: Int): Page<E>

    fun getContentById(contentId: ID): E

    fun saveContent(entity: E)

    fun updateContent(entity: E)

    fun markContentAsHide(contentId: ID)

    fun markContentAsUnHide(contentId: ID)

    fun markContentAsArchived(contentId: ID)

    fun markContentAsUnArchived(contentId: ID)

    fun deleteContent(entity: E)
}