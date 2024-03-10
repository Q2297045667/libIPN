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

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.DiffuseLighting
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.ClickEvent
//import net.minecraft.text.LiteralTextContent
import net.minecraft.text.MutableText
import net.minecraft.text.Style
import net.minecraft.text.Text
//import net.minecraft.text.TranslatableTextContent
import net.minecraft.util.Formatting
import net.minecraft.util.math.MathHelper

typealias Text = Text
typealias TextSerializer = Text.Serializer
//typealias LiteralText = LiteralTextContent
typealias MutableText = MutableText
//typealias TranslatableText = TranslatableTextContent

typealias TextRenderer = TextRenderer

typealias RenderSystem = RenderSystem

typealias DiffuseLighting = DiffuseLighting
typealias GlStateManager = GlStateManager
typealias SrcFactor = GlStateManager.SrcFactor
typealias DstFactor = GlStateManager.DstFactor

typealias MatrixStack = MatrixStack
typealias DrawContext = DrawContext
typealias RenderLayer = RenderLayer
typealias MathHelper = MathHelper
typealias Style = Style
typealias ClickEvent = ClickEvent
typealias ClickEventAction = ClickEvent.Action
typealias Formatting = Formatting

fun getTranslatable(s: String, vararg args: Any): Text = Text.translatable(s, *args)

fun getLiteral(s: String): Text = Text.literal(s)

val Text.`(formattedText)`
    get() = (this as MutableText).toString()
