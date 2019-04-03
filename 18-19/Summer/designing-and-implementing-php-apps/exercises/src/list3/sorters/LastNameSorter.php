<?php


namespace list3\sorters;


use list3\Application;

class LastNameSorter implements ListSorter {

    public function sort(array $list): array {
        $newList = $list;
        usort($newList, function (Application $application1, Application $application2) {
            //Not ok with LSP?
            return strcmp($application1->getStudent()->getLastName(), $application2->getStudent()->getLastName());
        });
        return $newList;
    }
}