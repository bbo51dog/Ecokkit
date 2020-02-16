package net.bbo51dog.ecokkit.api

import net.bbo51dog.ecokkit.repository.UserRepository

object EcokkitAPI : IEcokkitAPI {

    private lateinit var repo: UserRepository

    public fun init(repo: UserRepository) {
        this.repo = repo
    }
}