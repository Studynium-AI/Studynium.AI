package com.StudyniumAI.androidApp.Model.JetpackDataStore

class LocalDataUtils {

    suspend fun updateName(name:String,dataStore: androidx.datastore.core.DataStore<com.StudyniumAI.androidApp.Model.JetpackDataStore.LocalData>) {
        dataStore.updateData {
            it.copy(
                name = name
            )
        }
    }
    suspend fun updateTheme(theme:String,dataStore: androidx.datastore.core.DataStore<com.StudyniumAI.androidApp.Model.JetpackDataStore.LocalData>) {
        dataStore.updateData {
            it.copy(
                theme = theme
            )
        }
    }
    suspend fun updateLoggedIn(loggedIn:Boolean,dataStore: androidx.datastore.core.DataStore<com.StudyniumAI.androidApp.Model.JetpackDataStore.LocalData>) {
        dataStore.updateData {
            it.copy(
                loggedIn = loggedIn
            )
        }
    }
    suspend fun updateMisc(countryCode:Int,gender: String,dataStore: androidx.datastore.core.DataStore<com.StudyniumAI.androidApp.Model.JetpackDataStore.LocalData>) {
        dataStore.updateData {
            it.copy(
                countryCode = countryCode,
                gender = gender
            )
        }
    }
    suspend fun updateModelMetas(modelName:String,mode:Enum<Mode>,dataStore: androidx.datastore.core.DataStore<com.StudyniumAI.androidApp.Model.JetpackDataStore.LocalData>,modelMetas: ModelMetas? = null): Int {
        /*
        updates the model info in the data store

        returns 0 if failed and 1 if succeeded
         */
        var responseCode = 1;
        dataStore.updateData {
            if (mode == Mode.REMOVE) {
                it.copy(
                    modelData = it.modelData.remove(modelName)
                )
            }
            else if (mode == Mode.UPDATE && modelMetas != null) {
                it.copy(
                    modelData = it.modelData.put(modelName, modelMetas)
                )
            }
            else if (mode == Mode.ADD && modelMetas != null) {
                it.copy(
                    modelData = it.modelData.put(modelName, modelMetas)
                )
            }
            else {
                responseCode--
                it.copy()
            }

        }
        return responseCode
    }
    suspend fun updateReg(name:String,theme:String,loggedIn:Boolean,countryCode:Int,gender: String,dataStore: androidx.datastore.core.DataStore<com.StudyniumAI.androidApp.Model.JetpackDataStore.LocalData>) {
        updateName(name,dataStore)
        updateTheme(theme,dataStore)
        updateLoggedIn(loggedIn,dataStore)
        updateMisc(countryCode,gender,dataStore)
    }
}