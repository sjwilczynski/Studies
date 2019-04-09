<?php


namespace list4\accountApp\classes;


use Slim\Http\Request;
use Slim\Http\Response;

class AccountController {

    /**
     * @var AccountRepository
     */
    private $accountRepository;

    /**
     * AccountController constructor.
     * @param AccountRepository $accountRepository
     */
    public function __construct(AccountRepository $accountRepository) {
        $this->accountRepository = $accountRepository;
    }


    public function getAccount(Request $request, Response $response, array $args) {
        $id = (int)$args["id"];
        $account = $this->accountRepository->getAccountById($id);
        return $response->withJson(Account::toJson($account));
    }

    public function getAllAccounts(Request $request, Response $response, array $args) {
        $accounts = $this->accountRepository->getAllAccounts();
        $accountsJson = array_map(function (Account $a) {
            return Account::toJson($a);
        }, $accounts);
        return $response->withJson($accountsJson);
    }

    public function updateAccount(Request $request, Response $response, array $args) {
        $id = (int)$args["id"];
        $account = $this->accountRepository->getAccountById($id);
        $data = $request->getParsedBody();
        $active = $data["active"] != null ? $data["active"] == "true" ? true : false : $account->isActive();
        $points = ($data["points"] != null ? $data["points"] : 0) + $account->getPoints();

        $account->setActive($active);
        $account->setPoints($points);
        $this->accountRepository->updateAccount($account);

        return $response;
    }

    public function createAccount(Request $request, Response $response, array $args) {
        $data = $request->getParsedBody();
        $email = $data["email"];
        $description = $data["description"];
        $account = new Account(0, $email, $description, 0, true);
        $this->accountRepository->createAccount($account);
        return $response;
    }
}