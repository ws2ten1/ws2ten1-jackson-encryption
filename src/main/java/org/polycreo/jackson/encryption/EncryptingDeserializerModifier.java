/*
 * Copyright 2019 the original author or authors.
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
package org.polycreo.jackson.encryption;

import java.util.Iterator;

import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

@RequiredArgsConstructor
class EncryptingDeserializerModifier extends BeanDeserializerModifier {
	
	private final TextEncryptor encryptor;
	
	
	@Override
	public BeanDeserializerBuilder updateBuilder(DeserializationConfig config, BeanDescription beanDesc,
			BeanDeserializerBuilder builder) {
		Iterator<SettableBeanProperty> itr = builder.getProperties();
		
		while (itr.hasNext()) {
			SettableBeanProperty p = itr.next();
			if (p.getAnnotation(EncryptedField.class) != null) {
				EncryptingJsonDeserializer updated = new EncryptingJsonDeserializer(encryptor);
				builder.addOrReplaceProperty(p.withValueDeserializer(updated), true);
			}
		}
		return builder;
	}
}
