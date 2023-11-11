package com.spigot.plugin.blockshuffle2;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayableBlocks {
    private static PlayableBlocks INSTANCE;

    private static final Map<Biome,Set<Material>> BIOME_BY_BLOCK_MAP = new HashMap<>();
    private PlayableBlocks(){
        prepareBlocksToCertainBiomes();
    }

    public static PlayableBlocks getInstance(){
        if (INSTANCE == null){
            INSTANCE = new PlayableBlocks();
        }

        return INSTANCE;
    }

    private void prepareBlocksToCertainBiomes() {

        Set<Material> helper;

        for (Biome biome : Biome.values()){

            helper = new HashSet<>();

            switch (biome){
                case BEACH: {
                    helper.addAll(BlockShuffleLibrary.SAND_BASE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case GROVE:
                case SNOWY_TAIGA: {
                    helper.addAll(BlockShuffleLibrary.SPRUCE_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.SNOW_BASE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case OCEAN: {
                    helper.addAll(BlockShuffleLibrary.OCEAN_BASE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case FROZEN_RIVER:
                case RIVER: {
                    helper.addAll(BlockShuffleLibrary.RIVER);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case SWAMP: {
                    helper.addAll(BlockShuffleLibrary.SWAMP);
                    helper.addAll(BlockShuffleLibrary.OAK_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case TAIGA: {
                    helper.addAll(BlockShuffleLibrary.SNOWLESS_TAIGA);
                    helper.addAll(BlockShuffleLibrary.SPRUCE_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case DESERT: {
                    helper.addAll(BlockShuffleLibrary.DESERT);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case FOREST: {
                    helper.addAll(BlockShuffleLibrary.OAK_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.BIRCH_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.FLOWER_FOREST_BASE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case JUNGLE: {
                    helper.addAll(BlockShuffleLibrary.JUNGLE_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.OAK_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.JUNGLE);
                    helper.addAll(BlockShuffleLibrary.BAMBOO_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case MEADOW: {
                    helper.addAll(BlockShuffleLibrary.MEADOW);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case PLAINS: {
                    helper.addAll(BlockShuffleLibrary.FLOWER_PLAINS_BASE);
                    helper.addAll(BlockShuffleLibrary.OAK_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.BEES);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case WINDSWEPT_SAVANNA:
                case SAVANNA_PLATEAU:
                case SAVANNA: {
                    helper.addAll(BlockShuffleLibrary.ACACIA_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case WOODED_BADLANDS: {
                    helper.addAll(BlockShuffleLibrary.OAK_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.BADLANDS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case SMALL_END_ISLANDS:
                case END_HIGHLANDS:
                case END_BARRENS:
                case END_MIDLANDS:
                case THE_END: {
                    helper.addAll(BlockShuffleLibrary.END);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case DEEP_DARK: {
                    helper.addAll(BlockShuffleLibrary.SCULK);
                    helper.addAll(BlockShuffleLibrary.DEEP_BASE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case DEEP_COLD_OCEAN:
                case COLD_OCEAN:
                case DEEP_OCEAN: {
                    helper.addAll(BlockShuffleLibrary.DEEP_OCEAN);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case DEEP_FROZEN_OCEAN:
                case ICE_SPIKES:
                case FROZEN_OCEAN:
                case FROZEN_PEAKS:
                case JAGGED_PEAKS: {
                    helper.addAll(BlockShuffleLibrary.SNOW_BASE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case LUSH_CAVES: {
                    helper.addAll(BlockShuffleLibrary.LUSH_CAVE);
                    helper.addAll(BlockShuffleLibrary.DEEP_BASE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case WARM_OCEAN: {
                    helper.addAll(BlockShuffleLibrary.WARM_OCEAN);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case DARK_FOREST: {
                    helper.addAll(BlockShuffleLibrary.DARK_OAK_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.MUSHROOMS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case SNOWY_BEACH: {
                    helper.addAll(BlockShuffleLibrary.SAND_BASE);
                    helper.addAll(BlockShuffleLibrary.SNOW_BASE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case STONY_PEAKS: {
                    helper.add(BlockShuffleLibrary.Emerald);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case WINDSWEPT_GRAVELLY_HILLS:
                case STONY_SHORE: {
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case BIRCH_FOREST:
                case OLD_GROWTH_BIRCH_FOREST: {
                    helper.addAll(BlockShuffleLibrary.BIRCH_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.FLOWER_FOREST_BASE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case CHERRY_GROVE: {
                    helper.addAll(BlockShuffleLibrary.CHERRY_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case SNOWY_PLAINS: {
                    helper.addAll(BlockShuffleLibrary.SNOW_BASE);
                    helper.addAll(BlockShuffleLibrary.SPRUCE_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case SNOWY_SLOPES: {
                    helper.addAll(BlockShuffleLibrary.SNOW_BASE);
                    helper.addAll(BlockShuffleLibrary.SNOWY_SLOPES);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case BAMBOO_JUNGLE: {
                    helper.addAll(BlockShuffleLibrary.BAMBOO_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.JUNGLE_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.OAK_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case SOUL_SAND_VALLEY:
                case CRIMSON_FOREST:
                case WARPED_FOREST:
                case NETHER_WASTES:
                case BASALT_DELTAS: {
                    helper.addAll(BlockShuffleLibrary.NETHER);
                    helper.addAll(BlockShuffleLibrary.CRIMSON_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.WARPED_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case FLOWER_FOREST: {
                    helper.addAll(BlockShuffleLibrary.FLOWER_FOREST);
                    helper.addAll(BlockShuffleLibrary.OAK_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.BIRCH_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case SPARSE_JUNGLE: {
                    helper.addAll(BlockShuffleLibrary.JUNGLE_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.OAK_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.JUNGLE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case DEEP_LUKEWARM_OCEAN:
                case LUKEWARM_OCEAN: {
                    helper.addAll(BlockShuffleLibrary.OCEAN_BASE);
                    helper.addAll(BlockShuffleLibrary.DEEP_OCEAN);
                    helper.addAll(BlockShuffleLibrary.TROPICAL_FISHES);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case MANGROVE_SWAMP: {
                    helper.addAll(BlockShuffleLibrary.MANGROVE_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case DRIPSTONE_CAVES: {
                    helper.addAll(BlockShuffleLibrary.DEEP_BASE);
                    helper.addAll(BlockShuffleLibrary.DRIPSTONE_CAVE);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case BADLANDS:
                case ERODED_BADLANDS: {
                    helper.addAll(BlockShuffleLibrary.BADLANDS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case MUSHROOM_FIELDS: {
                    helper.addAll(BlockShuffleLibrary.MUSHROOM_FIELDS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case WINDSWEPT_FOREST:
                case WINDSWEPT_HILLS: {
                    helper.addAll(BlockShuffleLibrary.OAK_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.SPRUCE_CRAFTS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case SUNFLOWER_PLAINS: {
                    helper.addAll(BlockShuffleLibrary.SUNFLOWER_PLAINS);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
                case OLD_GROWTH_SPRUCE_TAIGA:
                case OLD_GROWTH_PINE_TAIGA: {
                    helper.addAll(BlockShuffleLibrary.SPRUCE_CRAFTS);
                    helper.addAll(BlockShuffleLibrary.OLD_GROWTH_TAIGA);
                    BIOME_BY_BLOCK_MAP.put(biome, helper);
                    break;
                }
            }
        }
    }

    public Map<Biome, Set<Material>> getMap() {
        return BIOME_BY_BLOCK_MAP;
    }

    public Map<Biome, Set<Material>> filterOutUnusedBiomes(Set<Biome> biomes){
        Map<Biome, Set<Material>> result = new HashMap<>();

        biomes.forEach(b -> result.put(b, BIOME_BY_BLOCK_MAP.get(b)));

        return result;
    }
}
