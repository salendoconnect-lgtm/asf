# End Expansion — Minecraft 1.20.1 / Fabric

Масштабное расширение The End по предоставленному ТЗ.

## Стек
- Minecraft Java Edition 1.20.1
- Fabric Loader 0.16.14
- Fabric API 0.92.2+1.20.1
- Yarn 1.20.1+build.10
- Java 17
- Fabric Loom 1.6.x

## Сборка

Требуется установленный Gradle 8.x и JDK 17.

```bash
gradle build
```

Готовый jar появится в `build/libs/`.

В текущей среде проекта не было установленного Gradle и отсутствовал сетевой доступ для загрузки Gradle/Fabric зависимостей, поэтому фактический `gradle build` здесь не запускался. JSON и ресурсный слой прошли статическую проверку: отсутствующих заявленных blockstate/model/texture/loot/entity/sound ресурсов не обнаружено.

## Запуск
1. Создать Fabric 1.20.1 профиль.
2. Положить собранный `end-expansion-1.0.0.jar` и Fabric API в `mods/`.
3. Для dedicated server использовать Java 17.
4. Создать новый мир или исследовать ещё не загруженные внешние области существующего End.

## Архитектура

`COMMON`:
- registry: blocks/items/entities/particles/sounds/effects/enchantments/structures
- серверная логика мобов и боссов
- Void Pressure
- прогрессия/advancements
- world/chunk generation
- loot/recipes/tags

`CLIENT`:
- renderer/model
- particles
- Ender Compass GUI
- client-only networking/UI

`SERVER`:
- урон, AI, спавн, босс-фазы, телепортация, давление Пустоты, торговля

## Важное решение для 1.20.1

Новые биомы регистрируются кодом и выбираются через mixin в `TheEndBiomeSource`: центральная область до ~320 блоков сохраняется ванильной, новые биомы появляются преимущественно во внешнем End.

Восемь новых структур реализованы как детерминированные server-side procedural structures при загрузке чанков. Это намеренно избегает хрупких `.nbt`-шаблонов и позволяет сохранять ванильные End City/End Gateway. Для новых биомов добавлены vanilla structure tags, чтобы существующие End Cities могли продолжать генерироваться.

## Progression

1. Outer End / Beyond the Islands
2. Ender Wastes / Ender Forests / Purple Swamps
3. Crystal Caves / Void Pressure
4. Ender Pearl Shards + Crystal Dust + Ender Essence
5. Distant Enderite
6. Enderite equipment
7. Ancient End Ruins / Fortress / Village / Observatory
8. Ancient Ender Key → Void Key
9. Crystal Temple → Crystal Core → Crystal Titan
10. Nullium / End Abyss
11. Void Key → Void Archon
12. Reality Core → final artifacts

## Боссы

### Crystal Titan
3 фазы, melee/ground strike/crystal attacks/beam/collapse, boss bar, loot table.

### Void Archon
5 фаз:
1. Observation
2. Main battle
3. Spatial rupture / teleport
4. Void Collapse / arena block destruction
5. Final phase

Перед атаками Archon есть 10-tick telegraph: частицы + звук → атака.

## Multiplayer / sync

Клиент не рассчитывает урон или игровые состояния. Compass mode передаётся C2S-пакетом и валидируется сервером. Boss phase и Void Pressure вычисляются сервером; boss bar и entity state синхронизируются vanilla/Fabric механизмами.

## Сохранение

- boss phase — entity NBT;
- Void Anchor charge — block state;
- compass mode — item NBT;
- cooldowns — vanilla player/item state;
- advancement progression — vanilla advancement storage;
- структуры — детерминированны по seed/chunk.

## Текстуры

Основной стиль — 16x16 pixel art, фиолетово-чёрная палитра. Armor layers — 64x32. Entity textures — 16x16.

## Возможные конфликты

- Моды, которые полностью заменяют `TheEndBiomeSource`, могут конфликтовать с mixin.
- Моды, которые полностью заменяют генератор/biome source End, могут потребовать ручного merge.
- Очень плотные моды worldgen могут увеличить стоимость chunk-load generation.
- Procedural structures не используют отдельный vanilla Structure registry, поэтому сторонние моды, ожидающие именно StructureFeature ID, не увидят их как vanilla structure.

## Проверено статически

- JSON: валиден.
- Все зарегистрированные блоки: blockstate + block model + item model + texture + loot table.
- Все зарегистрированные предметы: item model + texture.
- Все 12 entity textures/loot tables.
- Все custom sound files присутствуют и перечислены в sounds.json.
- Нет TODO/FIXME/пустых технических заглушек в main source.
