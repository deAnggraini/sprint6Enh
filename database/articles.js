const articles = [
    {
        id: 1,
        title: '',
        sort_desc: '',
        desc: '',
        created_by: '',
        created_date: '',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    }
]

module.exports.search = [
    {
        id: 1,
        parent: 'PAKAR',
        items: [
            { id: 1, title: 'Tahapan' },
            { id: 2, title: 'Tahapan Gold' },
            { id: 3, title: 'TAHAKA' },
        ]
    },
    {
        id: 2,
        parent: 'FAQ',
        items: [
            { id: 1, title: 'Bagaimana solusi ketika Teller melakukan input kode penalti pada nasabah' },
            { id: 2, title: 'Apakah bisa membuka Deposito dari Data RTGS Masuk?' },
        ]
    },
];
module.exports.articles = articles;
