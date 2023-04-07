window.onload = () => {
    const runAllScripts = () => {
        initializeAllAccordions()
    }

    const listenForUrlChanges = () => {
        let url = location.href
        document.body.addEventListener(
            'click',
            () => {
                requestAnimationFrame(() => {
                    if (url !== location.href) {
                        runAllScripts()
                        url = location.href
                    }
                })
            },
            true
        )
    }

    const initializeAllAccordions = () => {
        const allAccordions = document.querySelectorAll('[data-role="Accordion"]');

        allAccordions.forEach((accordion) => {
            const accordionHeader = accordion.querySelector('[data-type="accordion-header"]')
            const accordionContent = accordion.querySelector('[data-type="accordion-content"]')

            accordionHeader.addEventListener('click', () => {
                if (accordionContent.style.maxHeight) {
                    accordionContent.style.maxHeight = ''
                } else {
                    accordionContent.style.maxHeight = `${accordionContent.scrollHeight}px`
                }
            })
        })
    }

    listenForUrlChanges()
    runAllScripts()
}