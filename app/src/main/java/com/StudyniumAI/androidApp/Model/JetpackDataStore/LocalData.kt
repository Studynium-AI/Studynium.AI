package com.StudyniumAI.androidApp.Model.JetpackDataStore

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.serialization.Serializable

@Serializable
data class LocalData (
    val name : String = "",
    val modelData : PersistentMap<String,ModelMetas> = persistentMapOf(), // Model Name is Key
    val theme : String = "",
    val loggedIn : Boolean = false,
    val countryCode: Int = 0,
    val gender: String = "",
    val aiAPIKeys: APIKeys = APIKeys(),
) {
    companion object
}

@Serializable
data class ModelMetas(
    //val modelName: String = "", //name of the model
    val modelDescription: String = "", //description of the model
    val modelParameters: String = "", //parameters in the model
    val size: Long = 0, //size in bytes
    val modelType: String = "", //type of model (e.g. text, image, audio)
    val modelPath: String = "", //path to the model
    val isLocal: Boolean = false, //whether the model is local or not
    val keyAPI: String = "", //key for the model if it is a cloud model
)

@Serializable
data class APIKeys(
    val geminiKey: String = "",
    val searchKey: String = ""
    //aXriv also doesn't need an api key as we are not using ad revenue in this
    //no need for wikimedia as the thing has no restrictions
)

enum class Mode {
    ADD,REMOVE,UPDATE
}