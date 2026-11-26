import test from 'node:test'
import assert from 'node:assert/strict'
import { createRequire } from 'node:module'
import { generate } from './generate.mjs'
const input = createRequire(import.meta.url)('@shwkj/shw-ui-tokens/tokens.json')
test('fixed package maps every token and is independent of input order', () => {
  const files = generate(input)
  assert.deepEqual(files, generate({ ...input, tokens: Object.fromEntries(Object.entries(input.tokens).reverse()) }))
  const kotlin = files['kotlin/top/shw/nui/NuiTokens.kt']
  assert.equal(kotlin.match(/^    val /gm).length, Object.keys(input.tokens).length)
  for (const [name, token] of Object.entries(input.tokens)) {
    const key = name.replace(/-([a-z])/g, (_, c) => c.toUpperCase())
    if (token.type === 'color') {
      for (const [mode, folder] of [['light','values'], ['dark','values-night']]) {
        assert.ok(files['res/'+folder+'/nui_colors.xml'].includes('name="nui_'+name.replaceAll('-','_')+'">'+token[mode]+'</color>'))
      }
    } else {
      const line = kotlin.split('\n').find(s => s.startsWith('    val '+key+' = '))
      for (const value of [token.light, token.dark]) assert.ok(line.includes(token.type === 'fontWeight' ? 'FontWeight('+value+')' : value+(token.type === 'fontSize' ? '.sp' : '.dp')))
    }
  }
})
test('rejects incompatible schema, malformed colors and unknown types', () => {
  for (const doc of [{...input, schemaVersion:2}, ...[{type:'color',light:'bad',dark:'#ffffff'}, {type:'time',light:4,dark:4}].map(x=>({schemaVersion:1,tokens:{demo:x}}))]) assert.throws(()=>generate(doc))
})
