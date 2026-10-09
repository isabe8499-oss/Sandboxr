package com.ujwal.sandboxr.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sandboxr.launcher.theme.SandboxrFontFamilies
import com.sandboxr.launcher.theme.SandboxrTheme
import com.sandboxr.launcher.ui.PhosphorIcon
import com.sandboxr.launcher.ui.PhosphorIconView

/**
 * Full GrapheneOS-style Home Settings Dialog / Screen.
 * Provides all customization options, layout controls, theming, gestures,
 * and security toggles matching GrapheneOS Launcher3.
 */
@Composable
fun HomeSettingsDialog(
    currentSettings: LauncherSettings,
    onSaveSettings: (LauncherSettings) -> Unit,
    onOpenOnboarding: () -> Unit = {},
    onDismissRequest: () -> Unit
) {
    var settings by remember { mutableStateOf(currentSettings) }

    Dialog(onDismissRequest = {
        onSaveSettings(settings)
        onDismissRequest()
    }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(SandboxrTheme.colors.surface1)
                .border(1.dp, SandboxrTheme.colors.glassBorder, RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "CONFIGURAÇÕES DA TELA INICIAL",
                            fontFamily = SandboxrFontFamilies.JetBrainsMono,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp,
                            color = SandboxrTheme.colors.primaryAccent
                        )
                        Text(
                            text = "Personalização do inicializador GrapheneOS",
                            fontFamily = SandboxrFontFamilies.Inter,
                            fontSize = 12.sp,
                            color = SandboxrTheme.colors.textSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(SandboxrTheme.colors.surface2)
                            .clickable {
                                onSaveSettings(settings)
                                onDismissRequest()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        PhosphorIconView(
                            icon = PhosphorIcon.X,
                            color = SandboxrTheme.colors.textSecondary,
                            size = 14.dp
                        )
                    }
                }

                // Settings List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(520.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // SECTION 1: GRID & LAYOUT
                    item {
                        SettingsSectionHeader("GRADE E LAYOUT")
                    }
                    item {
                        GridSizeSelector(
                            selectedColumns = settings.gridColumns,
                            selectedRows = settings.gridRows,
                            onSelect = { cols, rows ->
                                settings = settings.copy(gridColumns = cols, gridRows = rows)
                            }
                        )
                    }
                    item {
                        HotseatCountSelector(
                            selectedCount = settings.hotseatIconCount,
                            onSelect = { count ->
                                settings = settings.copy(hotseatIconCount = count)
                            }
                        )
                    }

                    // SECTION 2: HOME SCREEN
                    item {
                        SettingsSectionHeader("TELA INICIAL")
                    }
                    item {
                        SettingsToggleRow(
                            title = "Adicionar ícones de apps à tela inicial",
                            subtitle = "Para novos aplicativos instalados",
                            checked = settings.autoAddNewAppsToHome,
                            onCheckedChange = { settings = settings.copy(autoAddNewAppsToHome = it) }
                        )
                    }
                    item {
                        SettingsToggleRow(
                            title = "Mostrar widget de resumo",
                            subtitle = "Cabeçalho com relógio e data, com atalhos para Relógio e Calendário",
                            checked = settings.showAtAGlance,
                            onCheckedChange = { settings = settings.copy(showAtAGlance = it) }
                        )
                    }
                    item {
                        SettingsToggleRow(
                            title = "Mostrar nomes dos apps na tela inicial",
                            subtitle = "Nomes abaixo dos ícones da tela inicial",
                            checked = settings.showHomeLabels,
                            onCheckedChange = { settings = settings.copy(showHomeLabels = it) }
                        )
                    }
                    item {
                        SettingsToggleRow(
                            title = "Permitir rotação da tela inicial",
                            subtitle = "Girar a tela inicial para o modo paisagem",
                            checked = settings.allowScreenRotation,
                            onCheckedChange = { settings = settings.copy(allowScreenRotation = it) }
                        )
                    }
                    item {
                        WallpaperDimmingSlider(
                            dimming = settings.wallpaperDimming,
                            onDimmingChange = { settings = settings.copy(wallpaperDimming = it) }
                        )
                    }

                    // SECTION 3: APP DRAWER
                    item {
                        SettingsSectionHeader("GAVETA DE APLICATIVOS")
                    }
                    item {
                        SettingsToggleRow(
                            title = "Mostrar barra de pesquisa na gaveta",
                            subtitle = "Barra para pesquisar apps e na internet",
                            checked = settings.showDrawerSearchBar,
                            onCheckedChange = { settings = settings.copy(showDrawerSearchBar = it) }
                        )
                    }
                    item {
                        SettingsToggleRow(
                            title = "Mostrar nomes dos apps na gaveta",
                            subtitle = "Nomes abaixo dos ícones da gaveta",
                            checked = settings.showDrawerLabels,
                            onCheckedChange = { settings = settings.copy(showDrawerLabels = it) }
                        )
                    }
                    item {
                        SearchEngineSelector(
                            selectedEngine = settings.searchEngine,
                            onSelect = { settings = settings.copy(searchEngine = it) }
                        )
                    }

                    // SECTION 4: THEMING & ICONS
                    item {
                        SettingsSectionHeader("TEMA E ESTILO")
                    }
                    item {
                        ThemeModeSelector(
                            selectedMode = settings.themeMode,
                            onSelect = { settings = settings.copy(themeMode = it) }
                        )
                    }
                    item {
                        SettingsToggleRow(
                            title = "Ícones temáticos",
                            subtitle = "Ícones monocromáticos que combinam com a cor do sistema",
                            checked = settings.themedIcons,
                            onCheckedChange = { settings = settings.copy(themedIcons = it) }
                        )
                    }
                    item {
                        IconShapeSelector(
                            selectedShape = settings.iconShape,
                            onSelect = { settings = settings.copy(iconShape = it) }
                        )
                    }

                    // SECTION 5: GESTURES
                    item {
                        SettingsSectionHeader("GESTOS")
                    }
                    item {
                        SettingsToggleRow(
                            title = "Deslizar para baixo para ver notificações",
                            subtitle = "Abrir o painel de notificações ao deslizar para baixo",
                            checked = settings.swipeDownForNotifications,
                            onCheckedChange = { settings = settings.copy(swipeDownForNotifications = it) }
                        )
                    }
                    item {
                        SettingsToggleRow(
                            title = "Toque duplo para bloquear",
                            subtitle = "Bloquear a tela tocando duas vezes em uma área vazia",
                            checked = settings.doubleTapToLock,
                            onCheckedChange = { settings = settings.copy(doubleTapToLock = it) }
                        )
                    }
                    item {
                        SettingsToggleRow(
                            title = "Deslizar para cima para ver todos os apps",
                            subtitle = "Abrir a gaveta deslizando de baixo para cima",
                            checked = settings.swipeUpForDrawer,
                            onCheckedChange = { settings = settings.copy(swipeUpForDrawer = it) }
                        )
                    }

                    // SECTION 6: SECURITY & PRIVACY
                    item {
                        SettingsSectionHeader("SEGURANÇA E PRIVACIDADE")
                    }
                    item {
                        SettingsToggleRow(
                            title = "Mostrar indicadores de isolamento",
                            subtitle = "Selo visual nos aplicativos isolados",
                            checked = settings.showSandboxIndicators,
                            onCheckedChange = { settings = settings.copy(showSandboxIndicators = it) }
                        )
                    }

                    // SECTION 7: HELP & ONBOARDING
                    item {
                        SettingsSectionHeader("AJUDA E INTRODUÇÃO")
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(SandboxrTheme.colors.surface2)
                                .clickable {
                                    onSaveSettings(settings)
                                    onDismissRequest()
                                    onOpenOnboarding()
                                }
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Guia de configuração dos perfis virtuais",
                                    fontFamily = SandboxrFontFamilies.Inter,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = SandboxrTheme.colors.textPrimary
                                )
                                Text(
                                    text = "Rever o guia de arquitetura e navegação em 6 etapas",
                                    fontFamily = SandboxrFontFamilies.Inter,
                                    fontSize = 11.sp,
                                    color = SandboxrTheme.colors.textSecondary
                                )
                            }
                            PhosphorIconView(
                                icon = PhosphorIcon.SHIELD,
                                color = SandboxrTheme.colors.primaryAccent,
                                size = 16.dp
                            )
                        }
                    }
                }

                // Done Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SandboxrTheme.colors.primaryAccent)
                        .clickable {
                            onSaveSettings(settings)
                            onDismissRequest()
                        }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Salvar e aplicar configurações",
                        fontFamily = SandboxrFontFamilies.Inter,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SandboxrTheme.colors.surface1
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        fontFamily = SandboxrFontFamilies.JetBrainsMono,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.0.sp,
        color = SandboxrTheme.colors.primaryAccent,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
private fun SettingsToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SandboxrTheme.colors.surface2)
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = SandboxrFontFamilies.Inter,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = SandboxrTheme.colors.textPrimary
            )
            Text(
                text = subtitle,
                fontFamily = SandboxrFontFamilies.Inter,
                fontSize = 11.sp,
                color = SandboxrTheme.colors.textSecondary
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = SandboxrTheme.colors.surface1,
                checkedTrackColor = SandboxrTheme.colors.primaryAccent,
                uncheckedThumbColor = SandboxrTheme.colors.textSecondary,
                uncheckedTrackColor = SandboxrTheme.colors.surface1
            )
        )
    }
}

@Composable
private fun GridSizeSelector(
    selectedColumns: Int,
    selectedRows: Int,
    onSelect: (Int, Int) -> Unit
) {
    val options = listOf(
        Pair(4, 4),
        Pair(4, 5),
        Pair(5, 5),
        Pair(6, 5)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SandboxrTheme.colors.surface2)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Tamanho da grade de apps",
            fontFamily = SandboxrFontFamilies.Inter,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = SandboxrTheme.colors.textPrimary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { (cols, rows) ->
                val isSelected = selectedColumns == cols && selectedRows == rows
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) SandboxrTheme.colors.primaryAccent else SandboxrTheme.colors.surface1)
                        .clickable { onSelect(cols, rows) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${cols}x${rows}",
                        fontFamily = SandboxrFontFamilies.JetBrainsMono,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) SandboxrTheme.colors.surface1 else SandboxrTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun HotseatCountSelector(
    selectedCount: Int,
    onSelect: (Int) -> Unit
) {
    val counts = listOf(4, 5, 6)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SandboxrTheme.colors.surface2)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Quantidade de ícones no dock",
            fontFamily = SandboxrFontFamilies.Inter,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = SandboxrTheme.colors.textPrimary
        )
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            counts.forEach { count ->
                val isSelected = selectedCount == count
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) SandboxrTheme.colors.primaryAccent else SandboxrTheme.colors.surface1)
                        .clickable { onSelect(count) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$count",
                        fontFamily = SandboxrFontFamilies.JetBrainsMono,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) SandboxrTheme.colors.surface1 else SandboxrTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeModeSelector(
    selectedMode: LauncherThemeMode,
    onSelect: (LauncherThemeMode) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SandboxrTheme.colors.surface2)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Modo do tema",
            fontFamily = SandboxrFontFamilies.Inter,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = SandboxrTheme.colors.textPrimary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            LauncherThemeMode.entries.forEach { mode ->
                val isSelected = selectedMode == mode
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) SandboxrTheme.colors.primaryAccent else SandboxrTheme.colors.surface1)
                        .clickable { onSelect(mode) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (mode) {
                            LauncherThemeMode.SYSTEM -> "System"
                            LauncherThemeMode.DARK -> "Escuro"
                            LauncherThemeMode.LIGHT -> "Claro"
                        },
                        fontFamily = SandboxrFontFamilies.Inter,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) SandboxrTheme.colors.surface1 else SandboxrTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun IconShapeSelector(
    selectedShape: String,
    onSelect: (String) -> Unit
) {
    val shapes = listOf("Squircle", "Circle", "Quadrado arredondado")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SandboxrTheme.colors.surface2)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Formato dos ícones",
            fontFamily = SandboxrFontFamilies.Inter,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = SandboxrTheme.colors.textPrimary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            shapes.forEach { shape ->
                val isSelected = selectedShape == shape
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) SandboxrTheme.colors.primaryAccent else SandboxrTheme.colors.surface1)
                        .clickable { onSelect(shape) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = shape,
                        fontFamily = SandboxrFontFamilies.Inter,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) SandboxrTheme.colors.surface1 else SandboxrTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchEngineSelector(
    selectedEngine: SearchEngine,
    onSelect: (SearchEngine) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SandboxrTheme.colors.surface2)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Provedor de pesquisa da gaveta",
            fontFamily = SandboxrFontFamilies.Inter,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = SandboxrTheme.colors.textPrimary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SearchEngine.entries.forEach { engine ->
                val isSelected = selectedEngine == engine
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) SandboxrTheme.colors.primaryAccent else SandboxrTheme.colors.surface1)
                        .clickable { onSelect(engine) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = engine.displayName.split(" ").first(),
                        fontFamily = SandboxrFontFamilies.Inter,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) SandboxrTheme.colors.surface1 else SandboxrTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun WallpaperDimmingSlider(
    dimming: Float,
    onDimmingChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SandboxrTheme.colors.surface2)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Escurecimento do papel de parede",
                fontFamily = SandboxrFontFamilies.Inter,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = SandboxrTheme.colors.textPrimary
            )
            Text(
                text = "${(dimming * 100).toInt()}%",
                fontFamily = SandboxrFontFamilies.JetBrainsMono,
                fontSize = 12.sp,
                color = SandboxrTheme.colors.primaryAccent
            )
        }
        Slider(
            value = dimming,
            onValueChange = onDimmingChange,
            valueRange = 0.0f..0.8f,
            colors = SliderDefaults.colors(
                thumbColor = SandboxrTheme.colors.primaryAccent,
                activeTrackColor = SandboxrTheme.colors.primaryAccent,
                inactiveTrackColor = SandboxrTheme.colors.surface1
            )
        )
    }
}
