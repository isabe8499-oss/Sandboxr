package com.ujwal.sandboxr.ui.onboarding

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sandboxr.launcher.theme.SandboxrFontFamilies
import com.sandboxr.launcher.theme.SandboxrTheme
import com.sandboxr.launcher.ui.PhosphorIcon
import com.sandboxr.launcher.ui.PhosphorIconView
import com.sandboxr.launcher.ui.rememberSandboxrHaptics
import kotlinx.coroutines.launch

/**
 * Data structure representing a single step in the user-friendly onboarding tour.
 */
data class OnboardingStep(
    val stepIndex: Int,
    val badge: String,
    val title: String,
    val subtitle: String,
    val icon: PhosphorIcon,
    val highlights: List<StepHighlight>,
    val helpfulTip: String? = null
)

data class StepHighlight(
    val title: String,
    val description: String,
    val icon: PhosphorIcon
)

/**
 * Full-screen GrapheneOS-style onboarding and tutorial flow for SANDBOXR.
 * Written in clear, non-technical language for the general public:
 * 1. Welcome & zero-tracking promise
 * 2. Separate spaces (profiles) for work, personal, and dual apps
 * 3. Simple navigation via drawer tabs and 1-tap switching
 * 4. Automatic privacy protection & tracker blocking
 * 5. Simple internet and network controls per space
 * 6. Setting as default home launcher
 */
@Composable
fun GrapheneOnboardingScreen(
    isDefaultLauncher: Boolean,
    onRequestDefaultLauncher: () -> Unit,
    onCompleteOnboarding: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptics = rememberSandboxrHaptics()
    val coroutineScope = rememberCoroutineScope()

    val steps = listOf(
        OnboardingStep(
            stepIndex = 1,
            badge = "ETAPA 1 DE 6 // INTRODUÇÃO",
            title = "Boas-vindas ao SANDBOXR",
            subtitle = "Uma tela inicial limpa, rápida e privada, criada para dar a você controle total do celular.",
            icon = PhosphorIcon.SHIELD,
            highlights = listOf(
                StepHighlight(
                    title = "Privacidade total",
                    description = "Sem rastreamento, análise de uso ou anúncios. Tudo o que você faz permanece no seu celular.",
                    icon = PhosphorIcon.LOCK
                ),
                StepHighlight(
                    title = "Rápido e econômico",
                    description = "Projetado para funcionar com fluidez sem consumir demais a bateria nem deixar o aparelho lento.",
                    icon = PhosphorIcon.CPU
                ),
                StepHighlight(
                    title = "Limpo e sem distrações",
                    description = "Um design simples e elegante inspirado no GrapheneOS, para uma experiência tranquila e organizada.",
                    icon = PhosphorIcon.GRID
                )
            ),
            helpfulTip = "Personalize o papel de parede, a grade e o formato dos ícones nas configurações da tela inicial."
        ),
        OnboardingStep(
            stepIndex = 2,
            badge = "ETAPA 2 DE 6 // ESPAÇOS SEPARADOS",
            title = "Espaços separados para seus apps",
            subtitle = "Mantenha sua vida pessoal, os apps de trabalho e as ferramentas privadas em espaços separados.",
            icon = PhosphorIcon.BOX,
            highlights = listOf(
                StepHighlight(
                    title = "Duas cópias de qualquer app",
                    description = "Entre em duas contas diferentes do WhatsApp, Telegram ou redes sociais ao mesmo tempo.",
                    icon = PhosphorIcon.PLUS
                ),
                StepHighlight(
                    title = "Fotos e arquivos separados",
                    description = "Arquivos, fotos e mensagens de um espaço ficam ocultos dos demais espaços.",
                    icon = PhosphorIcon.DATABASE
                ),
                StepHighlight(
                    title = "Sem configuração técnica",
                    description = "Crie um espaço em segundos, com nome e cor personalizados. Tudo fica pronto para usar.",
                    icon = PhosphorIcon.SHIELD
                )
            ),
            helpfulTip = "Toque em '+ Novo perfil' na gaveta de apps para criar um novo espaço."
        ),
        OnboardingStep(
            stepIndex = 3,
            badge = "ETAPA 3 DE 6 // NAVEGAÇÃO SIMPLES",
            title = "Alterne facilmente entre espaços",
            subtitle = "Alterne entre perfis com um toque, diretamente da gaveta de apps ou da tela inicial.",
            icon = PhosphorIcon.GRID,
            highlights = listOf(
                StepHighlight(
                    title = "Abas no topo da gaveta",
                    description = "Deslize para cima para ver os apps e toque em abas como 'Pessoal', 'Trabalho' ou nos espaços personalizados.",
                    icon = PhosphorIcon.USER
                ),
                StepHighlight(
                    title = "Seletor com um toque",
                    description = "A barra superior mostra o espaço ativo. Toque nela para alternar ou adicionar espaços.",
                    icon = PhosphorIcon.CARET_DOWN
                ),
                StepHighlight(
                    title = "Mantenha pressionado para ver ações",
                    description = "Mantenha um ícone pressionado para copiá-lo para outro espaço ou fixá-lo na tela inicial.",
                    icon = PhosphorIcon.SPARKLE
                )
            ),
            helpfulTip = "Deslize para cima na tela inicial para abrir a gaveta de apps e ver as abas dos perfis."
        ),
        OnboardingStep(
            stepIndex = 4,
            badge = "ETAPA 4 DE 6 // PROTEÇÃO DE PRIVACIDADE",
            title = "Impeça o rastreamento pelos apps",
            subtitle = "O SANDBOXR protege automaticamente os identificadores do dispositivo para dificultar a associação entre seus perfis.",
            icon = PhosphorIcon.LOCK,
            highlights = listOf(
                StepHighlight(
                    title = "Identidade própria por espaço",
                    description = "Cada perfil se apresenta aos apps como um dispositivo diferente, mantendo suas contas separadas.",
                    icon = PhosphorIcon.LOCK
                ),
                StepHighlight(
                    title = "Bloqueia rastreamento de anúncios",
                    description = "Ajuda a impedir que redes de anúncios e apps sociais reconheçam seu celular físico.",
                    icon = PhosphorIcon.SHIELD
                ),
                StepHighlight(
                    title = "Funciona automaticamente",
                    description = "A proteção fica ativa por padrão. Não é necessário configurar opções complicadas.",
                    icon = PhosphorIcon.CHECK
                )
            ),
            helpfulTip = "Os apps em espaços privados não conseguem ver o número de série real do dispositivo."
        ),
        OnboardingStep(
            stepIndex = 5,
            badge = "ETAPA 5 DE 6 // CONTROLE DA INTERNET",
            title = "Controle como os apps se conectam",
            subtitle = "Escolha se um espaço se conecta normalmente, usa VPN ou proxy, ou fica totalmente sem internet.",
            icon = PhosphorIcon.GLOBE,
            highlights = listOf(
                StepHighlight(
                    title = "Opções de VPN e proxy",
                    description = "Encaminhe os apps de trabalho por uma VPN segura enquanto os pessoais usam Wi-Fi ou dados móveis.",
                    icon = PhosphorIcon.GLOBE
                ),
                StepHighlight(
                    title = "Modo totalmente offline",
                    description = "Desative a internet para apps sensíveis, impedindo que enviem dados pela rede.",
                    icon = PhosphorIcon.LOCK
                ),
                StepHighlight(
                    title = "Consultas privadas na internet",
                    description = "Ajuda a proteger os sites acessados contra monitoramento pelo provedor de internet.",
                    icon = PhosphorIcon.SHIELD
                )
            ),
            helpfulTip = "Altere o modo de internet de qualquer perfil nas configurações do perfil."
        ),
        OnboardingStep(
            stepIndex = 6,
            badge = "ETAPA 6 DE 6 // TUDO PRONTO",
            title = "Use o SANDBOXR como tela inicial",
            subtitle = "Defina o SANDBOXR como inicializador padrão para usar gestos fluidos e recursos de privacidade na tela inicial.",
            icon = PhosphorIcon.HOME,
            highlights = listOf(
                StepHighlight(
                    title = "Gestos simples no dia a dia",
                    description = "Deslize para cima para ver os apps, para baixo para ver notificações e toque duas vezes em uma área vazia para bloquear.",
                    icon = PhosphorIcon.ARROW_UP
                ),
                StepHighlight(
                    title = "Relógio e data úteis",
                    description = "Toque no horário para abrir o relógio ou na data para abrir o calendário.",
                    icon = PhosphorIcon.CLOUD_SUN
                ),
                StepHighlight(
                    title = "Consulte novamente quando quiser",
                    description = "Reabra este guia quando quiser mantendo o papel de parede da tela inicial pressionado.",
                    icon = PhosphorIcon.GEAR
                )
            ),
            helpfulTip = "Toque em 'Definir como tela inicial padrão' abaixo e depois em 'Começar'!"
        )
    )

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { steps.size })

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(SandboxrTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                // Top Header: Step Counter & Skip Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(SandboxrTheme.colors.primaryAccent.copy(alpha = 0.15f))
                                .border(1.dp, SandboxrTheme.colors.primaryAccent.copy(alpha = 0.35f), RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            PhosphorIconView(
                                icon = PhosphorIcon.SHIELD,
                                color = SandboxrTheme.colors.primaryAccent,
                                size = 14.dp
                            )
                        }

                        Text(
                            text = "SANDBOXR // STEP ${pagerState.currentPage + 1} OF ${steps.size}",
                            fontFamily = SandboxrFontFamilies.JetBrainsMono,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.0.sp,
                            color = SandboxrTheme.colors.primaryAccent
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SandboxrTheme.colors.surface2)
                            .clickable {
                                haptics.onCardPress()
                                onCompleteOnboarding()
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "PULAR",
                            fontFamily = SandboxrFontFamilies.JetBrainsMono,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.0.sp,
                            color = SandboxrTheme.colors.textSecondary
                        )
                    }
                }

                // Horizontal Pager for the 6 setup steps
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { page ->
                    val step = steps[page]
                    OnboardingStepCard(
                        step = step,
                        isLastPage = page == steps.size - 1,
                        isDefaultLauncher = isDefaultLauncher,
                        onRequestDefaultLauncher = onRequestDefaultLauncher
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom Navigation Bar: Back, Indicators, and Next/Finish
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back Button (or spacer on first page)
                    if (pagerState.currentPage > 0) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(SandboxrTheme.colors.surface2)
                                .clickable {
                                    haptics.onCardPress()
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = "VOLTAR",
                                fontFamily = SandboxrFontFamilies.JetBrainsMono,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SandboxrTheme.colors.textPrimary
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(64.dp))
                    }

                    // Dot Page Indicators
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(steps.size) { index ->
                            val isSelected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .width(if (isSelected) 20.dp else 6.dp)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(
                                        if (isSelected) SandboxrTheme.colors.primaryAccent
                                        else SandboxrTheme.colors.surface2
                                    )
                                    .clickable {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    }
                            )
                        }
                    }

                    // Next or Get Started Button
                    val isLastPage = pagerState.currentPage == steps.size - 1
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SandboxrTheme.colors.primaryAccent)
                            .clickable {
                                haptics.onCardPress()
                                if (isLastPage) {
                                    onCompleteOnboarding()
                                } else {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                            }
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = if (isLastPage) "COMEÇAR" else "PRÓXIMO",
                            fontFamily = SandboxrFontFamilies.JetBrainsMono,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.0.sp,
                            color = SandboxrTheme.colors.surface1
                        )
                    }
                }
            }
        }
    }
}

/**
 * Slide card layout for a single onboarding tutorial page.
 */
@Composable
private fun OnboardingStepCard(
    step: OnboardingStep,
    isLastPage: Boolean,
    isDefaultLauncher: Boolean,
    onRequestDefaultLauncher: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card with Icon & Title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(SandboxrTheme.colors.surface1)
                .border(1.dp, SandboxrTheme.colors.glassBorder, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SandboxrTheme.colors.surface2)
                            .border(1.dp, SandboxrTheme.colors.glassBorder, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        PhosphorIconView(
                            icon = step.icon,
                            color = SandboxrTheme.colors.primaryAccent,
                            size = 22.dp
                        )
                    }

                    Text(
                        text = step.badge,
                        fontFamily = SandboxrFontFamilies.JetBrainsMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = SandboxrTheme.colors.textSecondary
                    )
                }

                Text(
                    text = step.title,
                    fontFamily = SandboxrFontFamilies.Inter,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SandboxrTheme.colors.textPrimary
                )

                Text(
                    text = step.subtitle,
                    fontFamily = SandboxrFontFamilies.Inter,
                    fontSize = 13.sp,
                    color = SandboxrTheme.colors.textSecondary,
                    lineHeight = 18.sp
                )
            }
        }

        // Feature Highlights
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            step.highlights.forEach { highlight ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SandboxrTheme.colors.surface2.copy(alpha = 0.7f))
                        .border(1.dp, SandboxrTheme.colors.glassBorder.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(SandboxrTheme.colors.surface1),
                        contentAlignment = Alignment.Center
                    ) {
                        PhosphorIconView(
                            icon = highlight.icon,
                            color = SandboxrTheme.colors.primaryAccent,
                            size = 14.dp
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = highlight.title,
                            fontFamily = SandboxrFontFamilies.Inter,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SandboxrTheme.colors.textPrimary
                        )

                        Text(
                            text = highlight.description,
                            fontFamily = SandboxrFontFamilies.Inter,
                            fontSize = 12.sp,
                            color = SandboxrTheme.colors.textSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Helpful Tip Banner
        step.helpfulTip?.let { tip ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SandboxrTheme.colors.surface2)
                    .border(1.dp, SandboxrTheme.colors.glassBorder, RoundedCornerShape(12.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(SandboxrTheme.colors.surface1),
                        contentAlignment = Alignment.Center
                    ) {
                        PhosphorIconView(
                            icon = PhosphorIcon.CHECK,
                            color = SandboxrTheme.colors.primaryAccent,
                            size = 10.dp
                        )
                    }

                    Text(
                        text = tip,
                        fontFamily = SandboxrFontFamilies.Inter,
                        fontSize = 11.sp,
                        color = SandboxrTheme.colors.textSecondary,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        // Last Page Special: Set Default Launcher Action
        if (isLastPage) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isDefaultLauncher) SandboxrTheme.colors.surface2 else SandboxrTheme.colors.primaryAccent.copy(alpha = 0.12f))
                    .border(
                        1.dp,
                        if (isDefaultLauncher) SandboxrTheme.colors.glassBorder else SandboxrTheme.colors.primaryAccent,
                        RoundedCornerShape(14.dp)
                    )
                    .clickable { onRequestDefaultLauncher() }
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isDefaultLauncher) "O SANDBOXR é seu inicializador padrão" else "Definir como tela inicial padrão",
                            fontFamily = SandboxrFontFamilies.Inter,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDefaultLauncher) SandboxrTheme.colors.textPrimary else SandboxrTheme.colors.primaryAccent
                        )
                        Text(
                            text = if (isDefaultLauncher) "Todos os gestos do sistema e o botão Início serão direcionados ao SANDBOXR." else "Toque aqui para definir o SANDBOXR como tela inicial principal.",
                            fontFamily = SandboxrFontFamilies.Inter,
                            fontSize = 11.sp,
                            color = SandboxrTheme.colors.textSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(if (isDefaultLauncher) SandboxrTheme.colors.primaryAccent else SandboxrTheme.colors.surface1),
                        contentAlignment = Alignment.Center
                    ) {
                        PhosphorIconView(
                            icon = if (isDefaultLauncher) PhosphorIcon.CHECK else PhosphorIcon.HOME,
                            color = if (isDefaultLauncher) SandboxrTheme.colors.surface1 else SandboxrTheme.colors.primaryAccent,
                            size = 14.dp
                        )
                    }
                }
            }
        }
    }
}
