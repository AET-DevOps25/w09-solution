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

export type Meal = {
    name: string;
    dish_type: string;
    labels: string[];
    favorite: boolean;
};

export type UserPreferences = {
    username: string;
    favoriteMeals: string[];
};

export type Recommendation = {
    recommendation: string;
};