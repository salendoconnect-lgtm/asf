# Final checklist

- [x] Fabric 1.20.1 project structure
- [x] 6 End biomes
- [x] outer-End biome routing
- [x] vanilla central End preserved
- [x] 40+ blocks
- [x] Enderite / Void Crystal / Pearl Shard / Nullium
- [x] Enderite tools
- [x] Enderite armor
- [x] Ender Compass GUI + C2S mode packet
- [x] Void Compass
- [x] Ender Teleporter with resource + cooldown + safe destination
- [x] Void Anchor with charge
- [x] 10 new mobs
- [x] AI goals / target selection / avoidance / teleport / ranged pulse / melee / group behavior
- [x] Ender Nomad custom trade system
- [x] 8 named procedural structures
- [x] structure-specific chest loot
- [x] 5 End City variants
- [x] Crystal Titan
- [x] Void Archon
- [x] boss bars
- [x] phase state persistence
- [x] custom particles
- [x] custom sounds + sounds.json
- [x] recipes
- [x] loot tables
- [x] Fortune / Silk Touch / Looting-aware drops where applicable
- [x] en_us / ru_ru
- [x] tags
- [x] advancements
- [x] Void Pressure
- [x] low-gravity biome behavior
- [x] Enderite equipment bonuses
- [x] Enderite mining particle burst
- [x] peaceful-mode target suppression
- [x] multiplayer server authority
- [x] NBT/state persistence
- [x] vanilla End City compatibility tag
- [x] resource/static validation

## Not silently faked

Minecraft 1.20.1 does not provide the modern data-component APIs from newer versions, so this project uses the 1.20.1 equivalents: item NBT, block state, entity NBT, Fabric networking and Fabric lifecycle events.

The eight requested structures are implemented as deterministic procedural structures rather than missing `.nbt` templates. They are real generated world content, not TODO placeholders.
