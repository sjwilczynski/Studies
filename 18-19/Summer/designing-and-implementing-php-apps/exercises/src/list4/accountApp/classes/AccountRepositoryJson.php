<?php


namespace list4\accountApp\classes;

//TODO: change this to database implementation cause it's awful
class AccountRepositoryJson implements AccountRepository {

    private $data_folder = __DIR__ . "/../data/";
    private $file_prefix = "account";

    public function getAccountById(int $id): Account {
        $json = $this->getAccountRepresentation($id);
        return Account::fromJson($json);
    }

    public function getAllAccounts(): array {
        $accounts = array();
        foreach (glob($this->data_folder . $this->file_prefix . '*') as $file) {
            array_push($accounts, Account::fromJson(file_get_contents($file)));
        }
        return $accounts;
    }

    public function createAccount(Account $account): void {
        $account->setId($this->getNextId());
        $this->createOrUpdate($account);
    }

    public function updateAccount(Account $account): void {
        $this->createOrUpdate($account);
    }

    public function deleteAccount(Account $account): void {
        $filename = $this->data_folder . $this->file_prefix . $account->getId();
        unlink($filename);
    }

    private function getAccountRepresentation(int $id): string {
        $json = file_get_contents($this->data_folder . $this->file_prefix . $id);
        return $json;
    }


    private function createOrUpdate(Account $account): void {
        $json = Account::toJson($account);
        $file = fopen($this->data_folder . $this->file_prefix . $account->getId(), "w");
        fwrite($file, $json);
        fclose($file);
    }

    private function getNextId(): int {
        $accounts = $this->getAllAccounts();
        return max(array_map(function (Account $a) {
                return $a->getId();
            }, $accounts)) + 1;
    }
}