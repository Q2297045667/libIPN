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

package org.anti_ad.mc.common.vanilla.alias

import net.minecraft.SharedConstants
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.GameOptions
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.resource.language.I18n
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.client.util.Window
import net.minecraft.client.world.ClientWorld
import net.minecraft.server.integrated.IntegratedServer
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier
import net.minecraft.util.Util
import net.minecraft.registry.DefaultedRegistry
import net.minecraft.registry.Registry
import net.minecraft.registry.Registries
import net.minecraft.client.network.ClientPlayerInteractionManager
import net.minecraft.client.sound.SoundInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.screen.ScreenHandlerFactory
import net.minecraft.sound.SoundEvent
import net.minecraft.world.GameMode

typealias MinecraftClient = MinecraftClient
typealias IntegratedServer = IntegratedServer

typealias Window = Window

typealias Identifier = Identifier

typealias Registry<T> = Registry<T>
typealias Registries = Registries
typealias DefaultedRegistry<T> = DefaultedRegistry<T>

typealias PositionedSoundInstance = PositionedSoundInstance
typealias SoundEvents = SoundEvents
typealias SoundInstance = SoundInstance
typealias Util = Util
typealias ClientWorld = ClientWorld

typealias SharedConstants = SharedConstants
typealias GameOptions = GameOptions
typealias KeyBinding = KeyBinding
typealias ClientPlayerInteractionManager = ClientPlayerInteractionManager
typealias PlayerEntity = PlayerEntity

typealias ScreenHandlerFactory = ScreenHandlerFactory

typealias SoundEvent = SoundEvent

typealias GameType = GameMode

@Suppress("ObjectPropertyName", "HasPlatformType")
inline val `(REGISTRIES-BLOCK_ENTITY_TYPES-IDS)`
    get() = Registries.BLOCK_ENTITY_TYPE.ids
@Suppress("ObjectPropertyName", "HasPlatformType")
inline val `(REGISTRIES-BLOCK-IDS)`
    get() = Registries.BLOCK.ids
@Suppress("ObjectPropertyName", "HasPlatformType")
inline val `(REGISTRIES-CONTAINER-IDS)`
    get() = Registries.SCREEN_HANDLER.ids
@Suppress("ObjectPropertyName", "HasPlatformType")
inline val `(REGISTRIES-ITEM-IDS)`
    get() = Registries.ITEM.ids

fun vanillaTranslate(string: String,
                     vararg objects: Any?): String {
    return I18n.translate(string, *objects)
}
