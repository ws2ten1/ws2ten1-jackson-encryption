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

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Jackson module to deserialize {@link com.fasterxml.jackson.annotation.JsonRawValue} annotated property.
 */
@SuppressWarnings("serial")
public class JacksonEncryptingDeserializationModule extends SimpleModule {
	
	private static final Version VERSION =
			new Version(1, 0, 0, null, "org.polycreo", "polycreo-jackson-encryption");
	
	
	/**
	 * Create instance.
	 */
	public JacksonEncryptingDeserializationModule(TextEncryptor encryptor) {
		super("JacksonEncryptingDeserializationModule", VERSION);
		setDeserializerModifier(new EncryptingDeserializerModifier(encryptor));
		setSerializerModifier(new DecryptingSerializerModifier(encryptor));
	}
}
