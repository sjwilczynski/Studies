<?php


namespace list4\accountsViewApp\classes;


use Slim\Http\Request;
use Slim\Http\Response;
use Slim\Views\Twig;

class AccountsController {

    /**
     * @var AccountsService
     */
    private $accountsService;

    /**
     * @var Twig
     */
    private $view;

    /**
     * AccountsController constructor.
     * @param Twig $view
     * @param AccountsService $accountsService
     */
    public function __construct(Twig $view, AccountsService $accountsService) {
        $this->accountsService = $accountsService;
        $this->view = $view;
    }

    public function showAccounts(Request $request, Response $response, array $args) {
        $accounts = $this->accountsService->getAccounts();
        return $this->view->render($response, 'accountsList.twig', [
            'accounts' => $accounts
        ]);
    }


}