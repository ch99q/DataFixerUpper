package com.mojang.datafixers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.datafixers.types.JsonOps;

import net.minecraft.server.v1_15_R1.DynamicOpsNBT;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.junit.Test;

import static org.junit.Assert.*;

public class DynamicTest {

    @Test
    public void testConvert() {
        JsonObject element = new JsonObject();
        element.addProperty("string", "hello world");
        element.addProperty("boolean_true", true);
        element.addProperty("boolean_false", false);
        element.addProperty("byte_1", (byte) 1);
        element.addProperty("byte_0", (byte) 0);
        element.addProperty("double", 10.5);
        element.addProperty("int", 11);
        element.addProperty("long", 12L);
        element.addProperty("float", 13.5F);
        element.addProperty("short", (short) 100);

        JsonObject properties = new JsonParser().parse(element.toString()).getAsJsonObject();

        final JsonObject dynamicJson = (JsonObject) Dynamic.convert(JsonOps.INSTANCE, JsonOps.INSTANCE, properties);

        assertEquals("hello world", dynamicJson.get("string").getAsString());
        assertEquals(true, dynamicJson.get("boolean_true").getAsBoolean());
        assertEquals(false, dynamicJson.get("boolean_false").getAsBoolean());
        assertEquals((byte) 1, dynamicJson.get("byte_1").getAsByte());
        assertEquals((byte) 0, dynamicJson.get("byte_0").getAsByte());
        assertEquals(10.5, dynamicJson.get("double").getAsDouble(), 10.5);
        assertEquals(11, dynamicJson.get("int").getAsInt());
        assertEquals(12L, dynamicJson.get("long").getAsLong());
        assertEquals(13.5F, dynamicJson.get("float").getAsFloat(), 13.5F);
        assertEquals((short) 100, dynamicJson.get("short").getAsShort());

        NBTTagCompound dynamicNBT = ((NBTTagCompound) Dynamic.convert(JsonOps.INSTANCE, DynamicOpsNBT.a, properties));

        assertEquals("hello world", dynamicNBT.getString("string"));
        assertEquals(true, dynamicNBT.getBoolean("boolean_true"));
        assertEquals(false, dynamicNBT.getBoolean("boolean_false"));
        assertEquals((byte) 1, dynamicNBT.getByte("byte_1"));
        assertEquals((byte) 0, dynamicNBT.getByte("byte_0"));
        assertEquals(10.5, dynamicNBT.getDouble("double"), 10.5);
        assertEquals(11, dynamicNBT.getInt("int"));
        assertEquals(12L, dynamicNBT.getLong("long"));
        assertEquals(13.5F, dynamicNBT.getFloat("float"), 13.5F);
        assertEquals((short) 100, dynamicNBT.getShort("short"));
    }
}