import { readFileSync, mkdirSync, writeFileSync } from 'node:fs'
import { createRequire } from 'node:module'
import { dirname, join, resolve } from 'node:path'
import { pathToFileURL } from 'node:url'

const require = createRequire(import.meta.url)
const installed = () => JSON.parse(readFileSync(require.resolve('@shwkj/shw-ui-tokens/tokens.json'), 'utf8'))
const color = /^#[0-9a-f]{6}$/i

export function generate(document) {
  if (document.schemaVersion !== 1 || !document.tokens || !Object.keys(document.tokens).length) {
    throw new Error('Expected a non-empty schemaVersion 1 token document')
  }
  const colors = { light: [], dark: [] }
  const properties = []
  for (const name of Object.keys(document.tokens).sort()) {
    if (!/^[a-z][a-z0-9]*(-[a-z][a-z0-9]*)*$/.test(name)) throw new Error('Invalid token name: ' + name)
    const { type, light, dark } = document.tokens[name]
    const key = name.replace(/-([a-z])/g, (_, letter) => letter.toUpperCase())
    if (type === 'color') {
      const resource = 'nui_' + name.replaceAll('-', '_')
      for (const mode of ['light', 'dark']) {
        const value = document.tokens[name][mode]
        if (!color.test(value)) throw new Error('Invalid RGB: ' + name)
        colors[mode].push('    <color name="' + resource + '">' + value + '</color>')
      }
      properties.push('    val ' + key + ' = Color(resources.getColor(R.color.' + resource + ', null))')
      continue
    }
    if (![light, dark].every(Number.isFinite)) throw new Error('Invalid number: ' + name)
    const value = n => {
      if (type === 'dimension') return n + '.dp'
      if (type === 'fontSize') return n + '.sp'
      if (type === 'fontWeight' && Number.isInteger(n) && n >= 1 && n <= 1000) return 'FontWeight(' + n + ')'
      throw new Error('Invalid type or weight: ' + name)
    }
    const day = value(light), night = value(dark)
    properties.push('    val ' + key + ' = ' + (day === night ? day : 'if (dark) ' + night + ' else ' + day))
  }
  const xml = mode => ['<resources>', ...colors[mode], '</resources>', ''].join('\n')
  return {
    'res/values/nui_colors.xml': xml('light'),
    'res/values-night/nui_colors.xml': xml('dark'),
    'kotlin/top/shw/nui/NuiTokens.kt': [
      'package top.shw.nui', '', 'import android.content.res.Resources',
      'import androidx.compose.ui.graphics.Color', 'import androidx.compose.ui.text.font.FontWeight',
      'import androidx.compose.ui.unit.dp', 'import androidx.compose.ui.unit.sp', '',
      'class NuiTokens internal constructor(resources: Resources, dark: Boolean) {',
      ...properties, '}', '',
    ].join('\n'),
  }
}

if (process.argv[1] && pathToFileURL(resolve(process.argv[1])).href === import.meta.url) {
  const result = generate(installed())
  for (const [file, content] of Object.entries(result)) {
    const target = join(process.argv[2] || 'nui/build/generated/shared-theme', file)
    mkdirSync(dirname(target), { recursive: true })
    writeFileSync(target, content)
  }
}
