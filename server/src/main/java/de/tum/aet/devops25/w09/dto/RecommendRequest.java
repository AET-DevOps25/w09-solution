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

package de.tum.aet.devops25.w09.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RecommendRequest(
    @JsonProperty("favorite_menu") List<String> favoriteMenu,
    @JsonProperty("todays_menu") List<String> todaysMenu
) {}
