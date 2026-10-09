package com.ujwal.sandboxr.ui.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ujwal.sandboxr.data.db.EnvironmentEntity
import com.sandboxr.launcher.theme.SandboxrColors
import com.sandboxr.launcher.theme.SandboxrFontFamilies
import com.sandboxr.launcher.theme.SandboxrSpacingTokens
import com.sandboxr.launcher.theme.SandboxrTheme
import com.sandboxr.launcher.ui.PhosphorIcon
import com.sandboxr.launcher.ui.PhosphorIconView

/**
 * Vault Tab for managing AES-256-GCM encrypted container backups and exports (.senv).
 */
@Composable
fun VaultTab(
    environments: List<EnvironmentEntity>,
    onNotice: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = SandboxrSpacingTokens.Normal)
            .padding(bottom = 100.dp) // clear bottom navigation bar
    ) {
        // Section Header
        Text(
            text = "COFRE CRIPTOGRAFADO DE AMBIENTES",
            style = SandboxrTheme.typography.label.copy(letterSpacing = 1.2.sp),
            color = SandboxrColors.PrimaryAccent
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Ambientes criptografados .senv",
            style = SandboxrTheme.typography.title,
            color = SandboxrColors.TextPrimaryDark
        )
        Text(
            text = "Pacotes criptografados com AES-256-GCM e derivação de chave PBKDF2. Faça backup dos APKs e dos dados isolados sem expô-los ao sistema anfitrião.",
            style = SandboxrTheme.typography.body.copy(fontSize = 13.sp),
            color = SandboxrColors.TextSecondaryDark,
            modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
        )

        // Actions Card
        val cardShape = RoundedCornerShape(16.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(cardShape)
                .background(SandboxrColors.SurfaceLevel1Dark)
                .border(1.dp, Color.White.copy(alpha = 0.08f), cardShape)
                .padding(18.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SandboxrColors.PrimaryAccent.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        PhosphorIconView(
                            icon = PhosphorIcon.DATABASE,
                            color = SandboxrColors.PrimaryAccent,
                            size = 20.dp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Backup e restauração do cofre",
                            style = SandboxrTheme.typography.body.copy(fontWeight = FontWeight.SemiBold),
                            color = SandboxrColors.TextPrimaryDark
                        )
                        Text(
                            text = "Proteção dos ambientes sem expor seus dados",
                            fontSize = 12.sp,
                            color = SandboxrColors.TextSecondaryDark
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = { onNotice("Selecione abaixo um ambiente para exportar como .senv") },
                        colors = ButtonDefaults.buttonColors(containerColor = SandboxrColors.PrimaryAccent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(44.dp)
                    ) {
                        PhosphorIconView(icon = PhosphorIcon.EXPORT, color = Color.White, size = 16.dp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Exportar cofre", fontSize = 12.sp, color = Color.White)
                    }

                    OutlinedButton(
                        onClick = { onNotice("Para importar, selecione um arquivo .senv no seletor de arquivos") },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = SandboxrColors.TextPrimaryDark),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(44.dp)
                    ) {
                        PhosphorIconView(icon = PhosphorIcon.PLUS, color = SandboxrColors.TextPrimaryDark, size = 16.dp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Importar .senv", fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Environments Available for Export
        Text(
            text = "AMBIENTES DISPONÍVEIS PARA BACKUP",
            style = SandboxrTheme.typography.label.copy(letterSpacing = 1.2.sp),
            color = SandboxrColors.TextSecondaryDark
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val virtualEnvs = environments.filter { !it.isSystem }
            if (virtualEnvs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SandboxrColors.SurfaceLevel1Dark)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum ambiente virtual foi criado. Crie um ambiente isolado para habilitar as exportações.",
                        fontSize = 12.sp,
                        color = SandboxrColors.TextSecondaryDark
                    )
                }
            } else {
                virtualEnvs.forEach { env ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SandboxrColors.SurfaceLevel1Dark)
                            .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(Color(env.colorTag), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = env.displayName,
                                        style = SandboxrTheme.typography.body.copy(fontWeight = FontWeight.Medium),
                                        color = SandboxrColors.TextPrimaryDark
                                    )
                                    Text(
                                        text = "UUID: ${env.id.take(8)}... | AES-256 Ready",
                                        fontFamily = SandboxrFontFamilies.JetBrainsMono,
                                        fontSize = 10.sp,
                                        color = SandboxrColors.TextSecondaryDark
                                    )
                                }
                            }

                            Button(
                                onClick = { onNotice("Exportando ${env.displayName}...") },
                                colors = ButtonDefaults.buttonColors(containerColor = SandboxrColors.SurfaceLevel2Dark),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text(
                                    text = "Backup",
                                    fontSize = 11.sp,
                                    color = SandboxrColors.PrimaryAccent
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
