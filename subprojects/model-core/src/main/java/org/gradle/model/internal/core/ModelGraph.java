/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.model.internal.core;

import com.google.common.collect.Maps;
import org.gradle.api.Nullable;
import org.gradle.model.internal.core.rule.describe.SimpleModelRuleDescriptor;

import java.util.Collections;
import java.util.Map;

public class ModelGraph {
    private final ModelNode root;
    private final Map<ModelPath, ModelNode> flattened = Maps.newTreeMap();

    public ModelGraph() {
        EmptyModelProjection projection = new EmptyModelProjection();
        this.root = new ModelNode(ModelPath.ROOT, new SimpleModelRuleDescriptor("<root>"), projection, projection);
    }

    public ModelNode getRoot() {
        return root;
    }

    public void add(ModelNode node) {
        flattened.put(node.getPath(), node);
    }

    public Map<ModelPath, ModelNode> getFlattened() {
        return Collections.unmodifiableMap(flattened);
    }

    @Nullable
    public ModelNode find(ModelPath path) {
        return flattened.get(path);
    }

    @Nullable
    public ModelNode remove(ModelPath path) {
        ModelNode parentNode = find(path.getParent());
        if (parentNode != null) {
            parentNode.removeLink(path.getName());
        }

        return flattened.remove(path);
    }
}
