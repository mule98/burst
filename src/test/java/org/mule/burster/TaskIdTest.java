package org.mule.burster;

import org.junit.jupiter.api.Test;

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
	void createFromString() {
		final TaskId result = TaskId.of("uuuuu");
		assertNotNull(result);
		assertNotNull(result.value());
		assertEquals(result.toString(), result.value());
	}
}