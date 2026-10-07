package com.endexpansion.registry;
import com.endexpansion.EndExpansion;
import net.minecraft.util.Identifier;
import java.util.Map;
import java.util.LinkedHashMap;
public final class EndStructures {
    public static final Map<String,Identifier> TYPES=new LinkedHashMap<>();
    public static final Identifier ANCIENT_END_RUINS=add("ancient_end_ruins");
    public static final Identifier END_FORTRESS=add("end_fortress");
    public static final Identifier CRYSTAL_TEMPLE=add("crystal_temple");
    public static final Identifier VOID_SHRINE=add("void_shrine");
    public static final Identifier ENDER_VILLAGE=add("ender_village");
    public static final Identifier FLOATING_CITADEL=add("floating_citadel");
    public static final Identifier ABYSSAL_GATE=add("abyssal_gate");
    public static final Identifier ANCIENT_OBSERVATORY=add("ancient_observatory");
    private static Identifier add(String id){Identifier i=new Identifier(EndExpansion.MOD_ID,id);TYPES.put(id,i);return i;}
    public static void register(){}
}
