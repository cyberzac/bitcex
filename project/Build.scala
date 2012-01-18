

/*
 * Copyright © 2012 Martin Zachrison.
 *
 *     This file is part of bitcex
 *
 *     bitcex is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     bitcex is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with bitcex.  If not, see <http://www.gnu.org/licenses/>.
 */

import sbt._
import Keys._

object BitcexBuild extends Build {
  lazy val root = Project(id = "bitcex",
    base = file(".")) aggregate(core, war)

  lazy val core = Project(id = "bitcex-core",
    base = file("bitcex-core"))

  lazy val war = Project(id = "bitcx-war",
    base = file("bitcex-war")) dependsOn(core)
}