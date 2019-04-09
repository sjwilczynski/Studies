<?php


namespace list4\accountApp\classes;


interface AccountRepository {

    public function getAccountById(int $id): Account;

    public function getAllAccounts(): array;

    public function createAccount(Account $account): void;

    public function updateAccount(Account $account): void;

    public function deleteAccount(Account $account): void;

}