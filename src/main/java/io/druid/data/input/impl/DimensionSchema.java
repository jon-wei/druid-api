/*
* Licensed to Metamarkets Group Inc. (Metamarkets) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. Metamarkets licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package io.druid.data.input.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = StringDimensionSchema.class)
@JsonSubTypes(value = {
    @JsonSubTypes.Type(name = "string", value = StringDimensionSchema.class),
    @JsonSubTypes.Type(name = "long", value = LongDimensionSchema.class),
    @JsonSubTypes.Type(name = "float", value = FloatDimensionSchema.class),
    @JsonSubTypes.Type(name = "spatial", value = NewSpatialDimensionSchema.class),
})
public abstract class DimensionSchema
{
  // main druid and druid-api should really use the same ValueType enum.
  // merge them when druid-api is merged back into the main repo
  public enum ValueType
  {
    FLOAT,
    LONG,
    STRING,
    COMPLEX;

    @JsonValue
    @Override
    public String toString()
    {
      return this.name().toUpperCase();
    }

    @JsonCreator
    public static ValueType fromString(String name)
    {
      return valueOf(name.toUpperCase());
    }
  }

  private final String name;

  protected DimensionSchema(String name)
  {
    Preconditions.checkArgument(name != null, "Dimension name cannot be null.");
    this.name = name;
  }

  @JsonProperty
  public String getName()
  {
    return name;
  };

  @JsonIgnore
  public abstract String getTypeName();

  @JsonIgnore
  public abstract ValueType getValueType();

  @Override
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DimensionSchema that = (DimensionSchema) o;

    return name.equals(that.name);

  }

  @Override
  public int hashCode()
  {
    return name.hashCode();
  }
}
