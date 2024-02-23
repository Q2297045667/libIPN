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

package org.anti_ad.mc.common.gui.widgets

import org.anti_ad.mc.common.config.IConfigOption
import org.anti_ad.mc.common.config.IConfigOptionToggleable
import org.anti_ad.mc.common.config.options.ConfigBoolean
import org.anti_ad.mc.common.config.options.ConfigButton
import org.anti_ad.mc.common.config.options.ConfigEnum
import org.anti_ad.mc.common.gui.NativeContext
import org.anti_ad.mc.common.gui.layout.Axis
import org.anti_ad.mc.common.gui.layout.BiFlex
import org.anti_ad.mc.common.gui.widgets.glue.IButtonWidget
import org.anti_ad.mc.common.math2d.Size
import org.anti_ad.mc.common.vanilla.VanillaSound
import org.anti_ad.mc.common.vanilla.alias.glue.I18n
import org.anti_ad.mc.common.vanilla.render.glue.rMeasureText
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT


fun ConfigBoolean.toWidget() = ConfigBooleanWidget(this)
fun ConfigEnum<*>.toWidget() = ConfigToggleableWidget(this) { it.value.toString() }
fun ConfigButton.toWidget() = ConfigButtonWidget(this)


abstract  class FlexWidgetBase: Widget() {
    open val flex = BiFlex(this,
                      Axis.HORIZONTAL)

}

abstract class ConfigWidgetBase<out T : IConfigOption>(val configOption: T) : FlexWidgetBase() {

    val resetButton = CustomButtonWidget { -> reset() }.apply {
        text = when(configOption.importance) {
            IConfigOption.Importance.IMPORTANT -> I18n.translate("libipn.common.gui.config.reset")
            IConfigOption.Importance.NORMAL -> "R"
        }
    }

    override fun render(context: NativeContext,
                        mouseX: Int,
                        mouseY: Int,
                        partialTicks: Float) {
        resetButton.active = resetButtonActive()
        super.render(context,
                     mouseX,
                     mouseY,
                     partialTicks)
    }

    open fun reset() {
        configOption.resetToDefault()
    }

    open fun resetButtonActive(): Boolean {
        return configOption.isModified
    }

    init {
        height = when (configOption.importance) {
            IConfigOption.Importance.IMPORTANT -> 20
            IConfigOption.Importance.NORMAL -> 15
        }
        resetButton.size = Size(height, height)
        val resetWidthPlus = when (configOption.importance) {
            IConfigOption.Importance.IMPORTANT -> 15
            IConfigOption.Importance.NORMAL -> 9
        }
        flex.reverse.add(resetButton,
                         rMeasureText(resetButton.text) + resetWidthPlus)
        flex.reverse.addSpace(1)
    }

}


class ConfigOptionToggleableButtonWidget(val configOptionToggleable: IConfigOptionToggleable,
                                         val textProvider: () -> String = { "" }) :
    CustomButtonWidget({ button ->
                           if (button == GLFW_MOUSE_BUTTON_LEFT) configOptionToggleable.toggleNext()
                           if (button == GLFW_MOUSE_BUTTON_RIGHT) configOptionToggleable.togglePrevious()
                       }) {
    override fun render(context: NativeContext,
                        mouseX: Int,
                        mouseY: Int,
                        partialTicks: Float) {
        toWidget.text = textProvider()
        super.render(context,
                     mouseX,
                     mouseY,
                     partialTicks)
    }
    init {
        toWidget.active = !configOptionToggleable.hidden
    }

}


class ConfigBooleanWidget(configOption: ConfigBoolean) : ConfigWidgetBase<ConfigBoolean>(configOption) {

    var trueText = if (configOption.importance == IConfigOption.Importance.IMPORTANT) {
        I18n.translate("libipn.common.gui.config.true")
    } else {
        I18n.translate("libipn.common.gui.config.yes")
    }
    var falseText = if (configOption.importance == IConfigOption.Importance.IMPORTANT) {
        I18n.translate("libipn.common.gui.config.false")
    } else {
        I18n.translate("libipn.common.gui.config.no")
    }
    val booleanButton = ConfigOptionToggleableButtonWidget(configOption) {
        if (configOption.booleanValue) trueText else falseText
    }

    init {
        flex.addAndFit(booleanButton)
    }
}

class ConfigToggleableWidget<T : IConfigOptionToggleable>(configOption: T,
                                                          var displayText: (T) -> String) : ConfigWidgetBase<T>(configOption) {

    val toggleButton = ConfigOptionToggleableButtonWidget(configOption) { displayText(configOption) }

    init {
        flex.addAndFit(toggleButton)
    }
}



class ConfigButtonWidget(configOption: ConfigButton) : ConfigWidgetBase<ConfigButton>(configOption) {
    val button = CustomButtonWidget().apply {
        text = configOption.info.buttonText
        clickEvent = { button ->
            if (button == 0) {
                VanillaSound.playClick()
                configOption.info.onClick(this)
            }
        }
    }

    init {
        flex.normal.offset = 2
        flex.reverse.offset = 2
        flex.addAndFit(button)
        resetButton.visible = false
    }
}


open class ConfigButtonInfo {
    open val buttonText: String = ""
    open fun onClick(widget: CustomButtonWidget) {}
}

open class ConfigButtonClickHandler {
    open fun onClick(guiClick: () -> Unit) {
        guiClick()
    }
}
