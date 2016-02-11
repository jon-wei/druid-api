/*
 * Druid - a distributed column store.
 * Copyright 2012 - 2015 Metamarkets Group Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.druid.data.input.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 */
public class NewSpatialDimensionSchema extends DimensionSchema
{
  private static final String TYPENAME = "spatial";

  private final List<String> dims;

  @JsonCreator
  public NewSpatialDimensionSchema(
      @JsonProperty("name") String name,
      @JsonProperty("dims") List<String> dims
  )
  {
    super(name);
    this.dims = dims;
  }

  @JsonProperty
  public List<String> getDims()
  {
    return dims;
  }

  @Override
  public String getTypeName()
  {
    return TYPENAME;
  }

  @Override
  @JsonIgnore
  public ValueType getValueType()
  {
    return ValueType.STRING;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    NewSpatialDimensionSchema that = (NewSpatialDimensionSchema) o;

    return dims != null ? dims.equals(that.dims) : that.dims == null;

  }

  @Override
  public int hashCode()
  {
    return dims != null ? dims.hashCode() : 0;
  }
}
