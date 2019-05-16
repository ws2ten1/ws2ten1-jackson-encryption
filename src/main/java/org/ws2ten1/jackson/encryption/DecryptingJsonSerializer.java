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
package org.ws2ten1.jackson.encryption;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * TODO miyamoto.daisuke.
 */
@RequiredArgsConstructor
class DecryptingJsonSerializer extends JsonSerializer<Object> {
	
	private final TextEncryptor encryptor;
	
	
	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		String decrypted = encryptor.decrypt(value.toString());
		gen.writeRawValue(decrypted);
	}
}
