/*
 * Copyright © TUM AET 2025 - 2025
 *
 * Licensed under the MIT License
 *
 * Authors: Benedikt Hofmann, Patrick Stoeckle, and other contributors
 *
 * SPDX-FileCopyrightText: 2025 TUM AET
 *
 * SPDX-License-Identifier: MIT
 */

package de.tum.aet.devops25.w09;

import de.tum.aet.devops25.w09.entity.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    Required for interacting with the database.
    A Spring Data JPA interface that provides ready-to-use database operations.
 */

public interface UserPreferenceRepository extends JpaRepository<UserPreferences, String> {

}
