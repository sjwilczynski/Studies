<?php


namespace list4\accountsViewApp\classes;


use GuzzleHttp\Client;
use GuzzleHttp\Exception\GuzzleException;
use list4\accountApp\classes\Account;
use RuntimeException;

class AccountServiceHttp implements AccountsService {

    /**
     * @var Client
     */
    private $client;

    /**
     * AccountServiceHttp constructor.
     * @param string $serverAddress
     */
    public function __construct(string $serverAddress) {
        $this->client = new Client(['base_uri' => $serverAddress]);
    }

    public function getAccounts(): array {
        try {
            $response = $this->client->request('GET', 'accounts');
            $accountJsons = json_decode($response->getBody()->getContents());
            $accounts = array_map(function (string $json) {
                return Account::fromJson($json);
            }, $accountJsons);
        } catch (GuzzleException $e) {
            throw new RuntimeException("Couldn't fetch accounts");
        }
        return $accounts;
    }
}