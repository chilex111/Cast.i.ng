package ng.i.cast.listener

import ng.i.cast.model.AuditionList

interface ProjectListener {
    fun projectListener(projectList: List<AuditionList>, status:Boolean)
}