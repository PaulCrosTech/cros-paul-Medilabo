import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig(({mode}) => {

    const isProduction = mode === 'production';

    const config = {
        plugins: [react()],
        server: {
            port: 9080,
        },
        envPrefix: ['MS_GATEWAY_SECURITY_', 'VITE_']
    };

    return isProduction ? config : {...config, envDir: '../../'};

});