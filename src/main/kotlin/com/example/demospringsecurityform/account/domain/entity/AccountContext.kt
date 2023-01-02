package com.example.demospringsecurityform.account.domain.entity

class AccountContext {
    companion object {
        val ACCOUNT_THREAD_LOCAL: ThreadLocal<Account> = ThreadLocal()
        fun setAccount(account: Account) {
            ACCOUNT_THREAD_LOCAL.set(account)
        }

        fun getAccount(): Account {
            return ACCOUNT_THREAD_LOCAL.get()
        }
    }
}