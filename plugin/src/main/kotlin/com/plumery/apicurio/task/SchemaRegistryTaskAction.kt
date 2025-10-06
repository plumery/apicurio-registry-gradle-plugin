package com.plumery.apicurio.task

import com.plumery.apicurio.task.compatibility.SchemaRegistryCompatibilityTaskAction
import com.plumery.apicurio.task.download.SchemaRegistryDownloadTaskAction
import com.plumery.apicurio.task.register.SchemaRegistryRegisterTaskAction

/**
 * An interface defining common behavioral structure for all the task actions which are invoked by the individual tasks.
 *
 * @see SchemaRegistryDownloadTaskAction
 * @see SchemaRegistryRegisterTaskAction
 * @see SchemaRegistryCompatibilityTaskAction
 */
internal interface SchemaRegistryTaskAction {
    /**
     * Executes the underlying task actions and returns the occured error count.
     *
     * @return error count, if any
     */
    fun run(): Int
}