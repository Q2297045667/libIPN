/*
 * Inventory Profiles Next
 *
 *   Copyright (c) 2019-2020 jsnimda <7615255+jsnimda@users.noreply.github.com>
 *   Copyright (c) 2021-2022 Plamen K. Kosseff <p.kosseff@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.anti_ad.mc.common.input

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import org.anti_ad.mc.libipn.Log
import org.anti_ad.mc.common.config.IConfigElementObject
import org.anti_ad.mc.common.input.KeybindSettings.ModifierKey.DIFFERENTIATE
import org.anti_ad.mc.common.input.KeybindSettings.ModifierKey.NORMAL

// ============
// Keybinds
// ============


class MainKeybind(defaultStorageString: String,
                  override val defaultSettings: KeybindSettings) : IKeybind {

    override val defaultKeyCodes = IKeybind.getKeyCodes(defaultStorageString)
    override var keyCodes = defaultKeyCodes
    override var settings = defaultSettings
}

@Suppress("MemberVisibilityCanBePrivate")
class AlternativeKeybind(val parent: IKeybind) : IKeybind { // keybind that inherit settings from parent
    override val defaultKeyCodes = listOf<Int>()
    override var keyCodes = defaultKeyCodes
    override val defaultSettings
        get() = parent.settings
    override var settings
        get() = mSettings ?: defaultSettings
        set(value) {
            mSettings = value
        }

    private var mSettings: KeybindSettings? = null
    override fun resetKeyCodesToDefault() {
        keyCodes = defaultKeyCodes
    }
    override fun resetSettingsToDefault() {
        mSettings = null
    }
    override val isSettingsModified
        get() = mSettings != null
}

// ============
// IKeybind
// ============

interface IKeybind : IConfigElementObject {
    val defaultKeyCodes: List<Int>
    var keyCodes: List<Int>
    val defaultSettings: KeybindSettings
    var settings: KeybindSettings

    fun isActivated() = GlobalInputHandler.isActivated(keyCodes,
                                                       settings)

    fun isPressing() = GlobalInputHandler.isPressing(keyCodes,
                                                     settings)

    val displayText
        get() = when (settings.modifierKey) {
            DIFFERENTIATE -> getDisplayText(keyCodes)
            NORMAL -> getDisplayTextModifier(keyCodes)
        }

    val isKeyCodesModified
        get() = defaultKeyCodes != keyCodes
    val isSettingsModified
        get() = defaultSettings != settings
    override val isModified
        get() = isKeyCodesModified || isSettingsModified

    fun resetKeyCodesToDefault() {
        keyCodes = defaultKeyCodes
    }

    fun resetSettingsToDefault() {
        settings = defaultSettings
    }

    override fun resetToDefault() {
        resetKeyCodesToDefault()
        resetSettingsToDefault()
    }

    fun KeybindSettings.toConfigElement() = ConfigKeybindSettings(defaultSettings,
                                                                  this)

    fun KeybindSettings.toJsonElement() = toConfigElement().toJsonElement()

    fun KeybindSettings.fromJsonElement(element: JsonElement): KeybindSettings {
        return toConfigElement().apply { fromJsonElement(element) }.settings
    }

    override fun toJsonElement() = JsonObject(mutableMapOf<String, JsonElement>().apply {
        if (isKeyCodesModified) {
            this["keys"] = JsonPrimitive(getStorageString(keyCodes))
        }
        if (isSettingsModified) {
            this["settings"] = settings.toJsonElement()
        }
    })

    override fun fromJsonObject(obj: JsonObject) {
        try {
            obj["settings"]?.let {
                settings = settings.fromJsonElement(it)
            }
            obj["keys"]?.let {
                keyCodes = getKeyCodes(it.jsonPrimitive.content)
            }
        } catch (e: Exception) {
            Log.warn("Failed to set config value for 'keys' from the JSON element '${obj["keys"]}'")
        }
    }


    companion object {

        fun getStorageString(keyCodes: List<Int>) = keyCodes.joinToString(",") { KeyCodes.getName(it) }

        fun getDisplayText(keyCodes: List<Int>) = keyCodes.joinToString(" + ") { KeyCodes.getFriendlyName(it) }

        fun getDisplayTextModifier(keyCodes: List<Int>) = keyCodes.joinToString(" + ") { KeyCodes.getModifierName(it) }

        fun getKeyCodes(storageString: String): List<Int> =
                storageString
                    .split(",")
                    .map {
                        KeyCodes.getKeyCode(it.trim())
                    }
                    .filter {
                        it != -1
                    }.distinct()
    }
}
