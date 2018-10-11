package org.mule.burster;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskIdTest {

	@Test
	void create() {
		final TaskId result = TaskId.create();
		assertNotNull(result);
		assertNotNull(result.value());
		assertEquals(result.toString(), result.value());
	}

	@Test
	void createFromNonValidString() {
		final TaskId result = TaskId.of(UUID.randomUUID());
		assertNotNull(result);
		assertNotNull(result.value());
		assertEquals(result.toString(), result.value());
	}
}